package com.hellonms.platforms.emp_orange.server.schedule_job.environment.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.schedule_job.environment.database.ScheduleJob4Database;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_info.Worker4NeInfoIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.operation_log.Worker4OperationLogIf;

public class ScheduleJob4DatabaseOrange extends ScheduleJob4Database {

	@Override
	protected String[][] prepareListPartition(EmpContext context, Date schedule_time) throws EmpException {
		List<String> partition_create_list = new ArrayList<String>();
		List<String> partition_drop_list = new ArrayList<String>();

		Worker4NeInfoIf worker4NeInfo = (Worker4NeInfoIf) WorkflowMap.getWorker(Worker4NeInfoIf.class);
		String[][] partition_indexs = worker4NeInfo.prepareListPartition(context, schedule_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		Worker4EventIf worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
		partition_indexs = worker4Event.prepareListPartition(context, schedule_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		Worker4OperationLogIf worker4OperationLog = (Worker4OperationLogIf) WorkflowMap.getWorker(Worker4OperationLogIf.class);
		partition_indexs = worker4OperationLog.prepareListPartition(context, schedule_time);
		for (String partition_index : partition_indexs[0]) {
			partition_create_list.add(partition_index);
		}
		for (String partition_index : partition_indexs[1]) {
			partition_drop_list.add(partition_index);
		}

		return new String[][] { partition_create_list.toArray(new String[0]), partition_drop_list.toArray(new String[0]) };
	}

}
