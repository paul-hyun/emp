/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.schedule_job.network.ne;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NMSIf;

/**
 * <p>
 * NMS 관련 관리기능을 수행한다.
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 28.
 * @modified 2016. 1. 28.
 * @author cchyun
 */
public class ScheduleJob4NMS implements ScheduleJobIf {

	private Worker4NMSIf worker4NMS;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(ScheduleJob4NMS.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4NMS.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4NMS = (Worker4NMSIf) WorkflowMap.getWorker(Worker4NMSIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		try {
			worker4NMS.createResourceNMS(context, schedule_time);
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(e, ERROR_CODE.ERROR_UNKNOWN);
		}
	}

}
