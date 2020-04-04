/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.link;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.link.Model4NetworkLink;
import com.hellonms.platforms.emp_util.cache.UtilCache;

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
public class Worker4NetworkLink implements Worker4NetworkLinkIf {

	private Dao4NetworkLinkIf dao4NetworkLink;

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NetworkLinkIf.class;
	}

	public void setDao4NetworkLink(Dao4NetworkLinkIf dao4NetworkLink) {
		this.dao4NetworkLink = dao4NetworkLink;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4NetworkLink == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NetworkLinkIf.class, getClass());
		}
		dao4NetworkLink.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Model4NetworkLink createNetworkLink(EmpContext context, Model4NetworkLink network_link) throws EmpException {
		Model4NetworkLink network_link_created = dao4NetworkLink.createNetworkLink(context, network_link);
		dao4NetworkLink.queryNextUpdate_seq_network(context);
		return network_link_created;
	}

	@Override
	public Model4NetworkLink[] queryListNetworkLink(EmpContext context, int startNo, int count) throws EmpException {
		return dao4NetworkLink.queryListNetworkLink(context, startNo, count);
	}

	@Override
	public Model4NetworkLink deleteNetworkLink(EmpContext context, int network_link_id) throws EmpException {
		Model4NetworkLink network_link_deleted = dao4NetworkLink.deleteNetworkLink(context, network_link_id);
		dao4NetworkLink.queryNextUpdate_seq_network(context);
		return network_link_deleted;
	}

	@Override
	public void truncate(EmpContext context) throws EmpException {
		dao4NetworkLink.truncate(context);
		UtilCache.removeAll();
	}

}
