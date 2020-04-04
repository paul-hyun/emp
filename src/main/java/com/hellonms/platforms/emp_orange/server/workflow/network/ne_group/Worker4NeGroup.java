/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.network.ne_group;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne.Worker4NeIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.TreeNode4NeGroup;

/**
 * <p>
 * NeGroup Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 19.
 * @modified 2015. 3. 19.
 * @author cchyun
 * 
 */
public class Worker4NeGroup implements Worker4NeGroupIf {

	private Dao4NeGroupIf dao4NeGroup;

	private Worker4NeIf worker4Ne;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4NeGroup.class);

	public void setDao4NeGroup(Dao4NeGroupIf dao4NeGroup) {
		this.dao4NeGroup = dao4NeGroup;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4NeGroupIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4NeGroup == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4NeGroupIf.class, getClass());
		}
		dao4NeGroup.initialize(context);

		worker4Ne = (Worker4NeIf) WorkflowMap.getWorker(Worker4NeIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4NeGroup.dispose(context);
	}

	@Override
	public Model4NeGroup createNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		ne_group = dao4NeGroup.createNeGroup(context, ne_group);

		queryNextUpdate_seq_network(context);
		return ne_group;
	}

	@Override
	public Model4NeGroup queryNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		return dao4NeGroup.queryNeGroup(context, ne_group_id);
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int startNo, int count) throws EmpException {
		return dao4NeGroup.queryListNeGroup(context, startNo, count);
	}

	@Override
	public Model4NeGroup[] queryListNeGroup(EmpContext context, int ne_group_id, int startNo, int count) throws EmpException {
		return dao4NeGroup.queryListNeGroup(context, ne_group_id, startNo, count);
	}

	@Override
	public Model4NeGroup[] queryAllNeGroup(EmpContext context) throws EmpException {
		return dao4NeGroup.queryAllNeGroup(context);
	}

	@Override
	public TreeNode4NeGroup queryTreeNeGroup(EmpContext context) throws EmpException {
		return dao4NeGroup.queryTreeNeGroup(context);
	}

	@Override
	public int queryCountNeGroup(EmpContext context) throws EmpException {
		return dao4NeGroup.queryCountNeGroup(context);
	}

	@Override
	public int queryCountNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		return dao4NeGroup.queryCountNeGroup(context, ne_group_id);
	}

	@Override
	public Model4NeGroup updateNeGroup(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		Model4NeGroup ne_group_updated = dao4NeGroup.updateNeGroup(context, ne_group);
		queryNextUpdate_seq_network(context);

		return ne_group_updated;
	}

	@Override
	public Model4NeGroup updateNeGroupMapLocation(EmpContext context, Model4NeGroup ne_group) throws EmpException {
		dao4NeGroup.updateNeGroupMapLocation(context, ne_group);
		queryNextUpdate_seq_network(context);

		Model4NeGroup ne_group_updated = queryNeGroup(context, ne_group.getNe_group_id());
		return ne_group_updated;
	}

	@Override
	public Model4NeGroup deleteNeGroup(EmpContext context, int ne_group_id) throws EmpException {
		Model4NeGroup[] ne_groups = dao4NeGroup.queryAllNeGroup(context, ne_group_id);
		int ne_group_count = 0;
		for (Model4NeGroup ne_group : ne_groups) {
			if (ne_group.getNe_group_id() != ne_group_id) {
				ne_group_count++;
			}
		}
		if (0 < ne_group_count) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_CHILDEXISTS, "NE_GROUP");
		}
		Model4Ne[] nes = worker4Ne.queryAllNe(context, ne_group_id);
		if (0 < nes.length) {
			throw new EmpException(ERROR_CODE_ORANGE.MODEL_CHILDEXISTS, "NE");
		}
		Model4NeGroup ne_group = dao4NeGroup.deleteNeGroup(context, ne_group_id);

		queryNextUpdate_seq_network(context);
		return ne_group;
	}

	protected long queryNextUpdate_seq_network(EmpContext context) throws EmpException {
		return dao4NeGroup.queryNextUpdate_seq_network(context);
	}

	@Override
	public void clearCache(EmpContext context) throws EmpException {
		dao4NeGroup.clearCache();
		queryNextUpdate_seq_network(context);
	}

}
