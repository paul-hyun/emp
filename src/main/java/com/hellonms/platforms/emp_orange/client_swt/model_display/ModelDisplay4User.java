/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.model_display;

import java.io.Serializable;

import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * Insert description of ModelDisplay4User.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 19.
 * @modified 2015. 3. 19.
 * @author jungsun
 * 
 */
@SuppressWarnings("serial")
public class ModelDisplay4User implements Serializable {

	private TablePageConfig<Model4User> tablePageConfig4User;

	private Model4UserGroup[] model4UserGroups;

	private Model4NeGroup[] model4NeGroups;

	public ModelDisplay4User() {
	}

	public ModelDisplay4User(int startNo, int count, Model4User[] model4Users, int totalCount, Model4UserGroup[] model4UserGroups, Model4NeGroup[] model4NeGroups) {
		tablePageConfig4User = new TablePageConfig<Model4User>(startNo, count, model4Users, totalCount);
		this.model4UserGroups = model4UserGroups;
		this.model4NeGroups = model4NeGroups;
	}

	public Model4User[] getModel4Users() {
		return tablePageConfig4User.getValues();
	}

	public TablePageConfig<Model4User> getTablePageConfig4User() {
		return tablePageConfig4User;
	}

	public Model4UserGroup[] getModel4UserGroups() {
		return model4UserGroups;
	}

	public Model4NeGroup[] getModel4NeGroups() {
		return model4NeGroups;
	}

}
