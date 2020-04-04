/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.schedule_job.security.user_session;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.transaction.UtilTransaction;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.util.UtilOperationLog;
import com.hellonms.platforms.emp_orange.server.workflow.security.operation_log.Worker4OperationLogIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_session.Worker4UserSessionIf;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_session.Model4UserSession;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 주기적으로 사용하지 않는 사용자 세션을 삭제한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 */
public class ScheduleJob4UserSession implements ScheduleJobIf {

	private int interval_seconds = 300;

	private Worker4UserSessionIf worker4UserSession;

	private Worker4OperationLogIf worker4OperationLog;

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4UserSession.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4UserSession.class;
	}

	public void setInterval_seconds(int interval_seconds) {
		this.interval_seconds = interval_seconds;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4UserSession = (Worker4UserSessionIf) WorkflowMap.getWorker(Worker4UserSessionIf.class);
		try {
			worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		} catch (Exception e) {
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		try {
			Model4UserSession[] user_sessions = worker4UserSession.queryListUserSessionByInterval(context, interval_seconds);

			for (Model4UserSession user_session : user_sessions) {
				Date start_time = new Date();
				Exception exception = null;
				try {
					worker4UserSession.logout(context, user_session.getUser_session_key());

					context.commit();
					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, context, UtilString.format("idle user session termination ok : {}", user_session));
					}
				} catch (EmpException e) {
					context.rollback();

					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, context, e, UtilString.format("idle user session termination fail : {}", user_session));
					}
					exception = e;
				} catch (Exception e) {
					context.rollback();

					if (blackBox.isEnabledFor(LEVEL.Site)) {
						blackBox.log(LEVEL.Site, context, e, UtilString.format("idle user session termination fail : {}", user_session));
					}
					exception = e;
				} finally {
					Date end_time = new Date();
					UtilTransaction.transaction_log(context.getTransaction_id_string(), user_session.getUser_id(), user_session.getUser_session_id(), user_session.getUser_account(), user_session.getUser_ip(), "SCHEDULE", OPERATION_CODE_ORANGE.SECURITY_USERSESSION_TERMINATE, start_time, end_time, null, exception == null, null, exception == null ? null : exception.getMessage());
					Model4OperationLog operation_log = UtilOperationLog.newInstance(context, OPERATION_CODE_ORANGE.SECURITY_USERSESSION_TERMINATE, exception, UtilString.format("terminate user session {}", user_session.getUser_session_id()));
					operation_log.setUser_account(user_session.getUser_account());
					operation_log.setUser_session_id(user_session.getUser_session_id());
					createOperationLog(context, operation_log);
				}
			}
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

	/**
	 * <p>
	 * 운용이력 생성
	 * <p>
	 * 
	 * @param context
	 * @param operation_log
	 */
	private void createOperationLog(EmpContext context, Model4OperationLog operation_log) {
		if (worker4OperationLog != null) {
			try {
				worker4OperationLog.createOperationLog(context, operation_log);
			} catch (EmpException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e);
				}
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e);
				}
			}
		}
	}

}
