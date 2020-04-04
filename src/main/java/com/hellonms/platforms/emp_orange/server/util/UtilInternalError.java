package com.hellonms.platforms.emp_orange.server.util;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_orange.server.workflow.fault.event.Worker4EventIf;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.GEN_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

public class UtilInternalError {

	private static Worker4EventIf worker4Event;

	private static final BlackBox blackBox = new BlackBox(UtilInternalError.class);

	public static void notifyInternalError(EmpContext context, int ne_id, String location_display, String description) {
		try {
			if (worker4Event == null) {
				worker4Event = (Worker4EventIf) WorkflowMap.getWorker(Worker4EventIf.class);
			}
			Model4Event event = new Model4Event();
			event.setNe_id(ne_id);
			event.setNe_info_code(0);
			event.setNe_info_index(0);
			event.setLocation_display(location_display);
			event.setEvent_code(EMP_MODEL_EVENT.INTERNAL_ERROR);
			event.setSeverity(SEVERITY.INFO);
			event.setGen_time(new Date());
			event.setGen_type(GEN_TYPE.SERVICE);
			event.setDescription(description);

			worker4Event.notifyEvent(context, event);
		} catch (Exception e) {
			if (blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, e);
			}
		}
	}

}
