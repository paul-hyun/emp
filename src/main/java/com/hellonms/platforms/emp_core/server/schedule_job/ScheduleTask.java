/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.server.schedule_job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 주기적 작업을 담당하는 Task
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class ScheduleTask implements Job {

	private static final BlackBox blackBox = new BlackBox(ScheduleTask.class);

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		String jobName = jobContext.getJobDetail().getKey().getName();
		try {
			@SuppressWarnings("unchecked")
			ScheduleJobIf scheduleJob = WorkflowMap.getScheduleJob((Class<? extends ScheduleJobIf>) Class.forName(jobName));

			EmpContext context = new EmpContext(scheduleJob);
			try {
				scheduleJob.execute(context, jobContext.getFireTime());
				context.commit();
			} catch (EmpException e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e, UtilString.format("ScheduleJob {} error", jobName));
				}
				context.rollback();
			} catch (Exception e) {
				if (blackBox.isEnabledFor(LEVEL.Fatal)) {
					blackBox.log(LEVEL.Fatal, context, e, UtilString.format("ScheduleJob {} error", jobName));
				}
				context.rollback();
			} finally {
				context.close();
			}
		} catch (EmpException e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e, UtilString.format("ScheduleJob {} error", jobName));
			}
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, null, e, UtilString.format("ScheduleJob {} error", jobName));
			}
		}

	}

}
