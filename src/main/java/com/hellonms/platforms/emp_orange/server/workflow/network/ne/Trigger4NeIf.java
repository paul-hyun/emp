/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.TriggerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionRequestIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionResponseIf;

/**
 * <p>
 * NE용 Trigger 정의
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 2.
 * @modified 2015. 4. 2.
 * @author cchyun
 *
 */
public interface Trigger4NeIf extends TriggerIf {

	/**
	 * <p>
	 * Trigger가 지원하는 EMP_MODEL_NE 조회
	 * </p>
	 *
	 * @return (null일 경우는 범용으로 사용 됨)
	 */
	public EMP_MODEL_NE getNe_def();

	/**
	 * <p>
	 * NE 생성 전에 수행할 명령어
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionRequestIf[] preCreateNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne postCreateNe(EmpContext context, Model4Ne ne, Model4NeSessionResponseIf[] responses) throws EmpException;

	/**
	 * <p>
	 * NE 수정 전에 수행할 명령어
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionRequestIf[] preUpdateNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne postUpdateNe(EmpContext context, Model4Ne ne, Model4NeSessionResponseIf[] responses) throws EmpException;

	/**
	 * <p>
	 * NE 삭제 전에 수행할 명령어
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4NeSessionRequestIf[] preDeleteNe(EmpContext context, Model4Ne ne) throws EmpException;

	/**
	 * <p>
	 * 명령어 수행후 분석결과 처리
	 * </p>
	 *
	 * @param context
	 * @param ne
	 * @return
	 * @throws EmpException
	 */
	public Model4Ne postDeleteNe(EmpContext context, Model4Ne ne, Model4NeSessionResponseIf[] responses) throws EmpException;

}
