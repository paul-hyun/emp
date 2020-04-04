/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.server.workflow.security.user_group;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * 사용자그룹 Worker
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 13.
 * @modified 2015. 3. 13.
 * @author cchyun
 * 
 */
public class Worker4UserGroup implements Worker4UserGroupIf {

	private Dao4UserGroupIf dao4UserGroup;

	@SuppressWarnings("unused")
	private static final BlackBox blackBox = new BlackBox(Worker4UserGroup.class);

	public void setDao4UserGroup(Dao4UserGroupIf dao4UserGroup) {
		this.dao4UserGroup = dao4UserGroup;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4UserGroupIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		if (dao4UserGroup == null) {
			throw new EmpException(ERROR_CODE_ORANGE.WORKFLOW_NOTSETTED, Dao4UserGroupIf.class, getClass());
		}
		dao4UserGroup.initialize(context);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		dao4UserGroup.dispose(context);
	}

	@Override
	public Model4UserGroup createUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException {
		return dao4UserGroup.createUserGroup(context, user_group);
	}

	@Override
	public Model4UserGroup queryUserGroup(EmpContext context, int user_group_id) throws EmpException {
		return dao4UserGroup.queryUserGroup(context, user_group_id);
	}

	@Override
	public Model4UserGroup[] queryListUserGroup(EmpContext context, int startNo, int count) throws EmpException {
		return dao4UserGroup.queryListUserGroup(context, startNo, count);
	}

	@Override
	public int queryCountUserGroup(EmpContext context) throws EmpException {
		return dao4UserGroup.queryCountUserGroup(context);
	}

	@Override
	public Model4UserGroup updateUserGroup(EmpContext context, Model4UserGroup user_group) throws EmpException {
		return dao4UserGroup.updateUserGroup(context, user_group);
	}

	@Override
	public Model4UserGroup deleteUserGroup(EmpContext context, int user_group_id) throws EmpException {
		return dao4UserGroup.deleteUserGroup(context, user_group_id);
	}

}
