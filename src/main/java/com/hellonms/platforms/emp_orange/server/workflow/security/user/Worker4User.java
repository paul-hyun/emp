/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.server.workflow.network.ne_group.Worker4NeGroupIf;
import com.hellonms.platforms.emp_orange.server.workflow.security.user_group.Worker4UserGroupIf;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * 사용자 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 13.
 * @modified 2015. 3. 13.
 * @author cchyun
 * 
 */
public class Worker4User implements Worker4UserIf {

	private Dao4UserIf dao4User;

	private Worker4UserGroupIf worker4UserGroup;

	private Worker4NeGroupIf worker4NeGroup;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4User.class);

	public void setDao4User(Dao4UserIf dao4User) {
		this.dao4User = dao4User;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4UserIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4User == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4UserIf.class, getClass());
		}
		dao4User.initialize(context);

		worker4UserGroup = (Worker4UserGroupIf) WorkflowMap.getWorker(Worker4UserGroupIf.class);
		try {
			worker4NeGroup = (Worker4NeGroupIf) WorkflowMap.getWorker(Worker4NeGroupIf.class);
		} catch (EmpException e) {
		}
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4User.dispose(context);
	}

	@Override
	public Model4User createUser(EmpContext context, Model4User user) throws EmpException {
		@SuppressWarnings("unused")
		Model4UserGroup user_group = worker4UserGroup.queryUserGroup(context, user.getUser_group_id());
		return dao4User.createUser(context, user);
	}

	@Override
	public Model4User queryUser(EmpContext context, int user_id) throws EmpException {
		return dao4User.queryUser(context, user_id);
	}

	@Override
	public Model4User queryUserByAccount(EmpContext context, String user_account) throws EmpException {
		return dao4User.queryUserByAccount(context, user_account);
	}

	@Override
	public Model4User[] queryListUser(EmpContext context, int startNo, int count) throws EmpException {
		return dao4User.queryListUser(context, startNo, count);
	}

	@Override
	public boolean equalPassword(EmpContext context, int user_id, String password) throws EmpException {
		return dao4User.equalPassword(context, user_id, password);
	}

	@Override
	public int queryCountUser(EmpContext context) throws EmpException {
		return dao4User.queryCountUser(context);
	}

	@Override
	public Model4User updateUser(EmpContext context, Model4User user) throws EmpException {
		@SuppressWarnings("unused")
		Model4UserGroup user_group = worker4UserGroup.queryUserGroup(context, user.getUser_group_id());
		user = dao4User.updateUser(context, user);
		if (worker4NeGroup != null) {
			worker4NeGroup.clearCache(context);
		}
		return user;
	}

	@Override
	public Model4User deleteUser(EmpContext context, int user_id) throws EmpException {
		Model4User user = dao4User.deleteUser(context, user_id);
		if (worker4NeGroup != null) {
			worker4NeGroup.clearCache(context);
		}
		return user;
	}

}
