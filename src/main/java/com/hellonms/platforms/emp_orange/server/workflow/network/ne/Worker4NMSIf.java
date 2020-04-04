/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne;

import java.util.Date;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;

/**
 * <p>
 * Worker for NMS
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 */
public interface Worker4NMSIf extends WorkerIf {

	public Model4ResourceNMS createResourceNMS(EmpContext context, Date collect_time) throws EmpException;

	public Model4ResourceNMS[] queryListResourceNMS(EmpContext context) throws EmpException;

}
