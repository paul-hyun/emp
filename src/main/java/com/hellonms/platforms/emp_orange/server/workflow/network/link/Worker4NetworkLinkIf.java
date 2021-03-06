/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.link;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;

/**
 * <p>
 * Network Link
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 25.
 * @modified 2016. 1. 25.
 * @author cchyun
 */
public interface Worker4NetworkLinkIf extends WorkerIf {

	public Model4NetworkLink createNetworkLink(EmpContext context, Model4NetworkLink network_link) throws EmpException;

	public Model4NetworkLink[] queryListNetworkLink(EmpContext context, int startNo, int count) throws EmpException;

	public Model4NetworkLink deleteNetworkLink(EmpContext context, int network_link_id) throws EmpException;

	/**
	 * <p>
	 * 관련 테이블 초기화
	 * </p>
	 * 
	 * @param context
	 * @throws EmpException
	 */
	public void truncate(EmpContext context) throws EmpException;

}
