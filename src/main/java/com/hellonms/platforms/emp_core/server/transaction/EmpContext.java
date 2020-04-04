/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.transaction;

import java.io.InputStream;
import java.sql.SQLException;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.key.UtilKey;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * Transaction단위의 resoure 관리
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public final class EmpContext {

	public enum DATABASE {
		POSTGRESQL("org.postgresql.Driver"), //
		UNKNOWN(), //
		;

		private String[] jdbc_drivers;

		private DATABASE(String... jdbc_drivers) {
			this.jdbc_drivers = jdbc_drivers;
		}

		public static DATABASE toEnum(String jdbc_driver) {
			for (DATABASE database : values()) {
				for (String aaa : database.jdbc_drivers) {
					if (aaa.equals(jdbc_driver)) {
						return database;
					}
				}
			}
			return UNKNOWN;
		}

	}

	private static String emp_home = ".";

	private static SqlSessionFactory sqlSessionFactory;

	private static DATABASE database;

	public static long transaction_wait = 0L;

	private static final BlackBox blackBox = new BlackBox(EmpContext.class);

	public static String getEMP_HOME() {
		return emp_home;
	}

	public static void setEMP_HOME(String emp_home) {
		EmpContext.emp_home = emp_home;
	}

	public static DATABASE getDatabase() {
		return database;
	}

	public static void initialize(InputStream is) throws EmpException {
		try {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			if (sqlSessionFactory.getConfiguration() != null && sqlSessionFactory.getConfiguration().getEnvironment() != null && sqlSessionFactory.getConfiguration().getEnvironment().getDataSource() != null) {
				String jdbc_driver = ((PooledDataSource) sqlSessionFactory.getConfiguration().getEnvironment().getDataSource()).getDriver();
				database = DATABASE.toEnum(jdbc_driver);
			}
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE_CORE.FILE_IO);
		}
	}

	@SuppressWarnings("unused")
	private final Object transaction_actor;

	private final long transaction_id;

	private String transaction_id_string;

	private final long open_timestamp;

	private long close_timestamp;

	private boolean auto_commit = true;

	private SqlSession sqlSession;

	private int user_id = 0;

	private int user_session_id = 0;

	private String user_account = "not_logined";

	private String user_ip = "127.0.0.1";

	public EmpContext(Object transaction_actor) {
		if (0 < transaction_wait) {
			try {
				Thread.sleep(transaction_wait);
			} catch (Exception e) {
			}
		}
		this.transaction_actor = transaction_actor;
		this.transaction_id = UtilKey.nextLong_id();
		this.open_timestamp = System.currentTimeMillis();
	}

	public long getTransaction_id() {
		return transaction_id;
	}

	/**
	 * <p>
	 * transaction_id 문자열 조회 (로그 저장 용)
	 * </p>
	 * 
	 * @return
	 */
	public String getTransaction_id_string() {
		if (transaction_id_string == null) {
			transaction_id_string = UtilString.lpad(Long.toHexString(transaction_id), 16);
		}
		return transaction_id_string;
	}

	public boolean isAuto_commit() {
		return auto_commit;
	}

	public void setAuto_commit(boolean auto_commit) {
		this.auto_commit = auto_commit;
		if (sqlSession != null) {
			try {
				sqlSession.getConnection().setAutoCommit(auto_commit);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * <p>
	 * sqlSession 조회 (최초에는 생성, 이후에는 재 사용 함)
	 * </p>
	 * 
	 * @return
	 */
	public SqlSession getSqlSession() {
		if (sqlSession == null) {
			sqlSession = sqlSessionFactory.openSession(auto_commit);
		}
		return sqlSession;
	}

	/**
	 * <p>
	 * Transaction을 반영한다.
	 * </p>
	 * 
	 */
	public void commit() {
		if (!auto_commit && sqlSession != null) {
			try {
				sqlSession.getConnection().commit();
			} catch (SQLException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, this, e);
				}
			}
		}
	}

	/**
	 * <p>
	 * Transaction을 취소한다.
	 * </p>
	 * 
	 */
	public void rollback() {
		if (!auto_commit && sqlSession != null) {
			try {
				sqlSession.getConnection().rollback();
			} catch (SQLException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, this, e);
				}
			}
		}
	}

	/**
	 * <p>
	 * Transaction을 종료한다.
	 * </p>
	 * 
	 */
	public void close() {
		this.close_timestamp = System.currentTimeMillis();

		if (sqlSession != null) {
			sqlSession.close();
			sqlSession = null;
		}
	}

	public long getOpen_timestamp() {
		return open_timestamp;
	}

	public long getClose_timestamp() {
		return close_timestamp;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_session_id() {
		return user_session_id;
	}

	public void setUser_session_id(int user_session_id) {
		this.user_session_id = user_session_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

}
