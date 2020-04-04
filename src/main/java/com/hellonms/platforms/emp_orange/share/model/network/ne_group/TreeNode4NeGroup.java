/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.network.ne_group;

import java.util.ArrayList;
import java.util.List;

import com.hellonms.platforms.emp_core.share.model.ModelIf;

/**
 * <p>
 * NE Group을 Tree 구조로 표현하기 위한 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 23.
 * @modified 2015. 3. 23.
 * @author cchyun
 * 
 */
@SuppressWarnings("serial")
public class TreeNode4NeGroup implements ModelIf {

	private Model4NeGroup ne_group;

	private boolean manage_ne_group;

	private List<TreeNode4NeGroup> child_list = new ArrayList<TreeNode4NeGroup>();

	public Model4NeGroup getNe_group() {
		return ne_group;
	}

	public void setNe_group(Model4NeGroup ne_group) {
		this.ne_group = ne_group;
	}

	public int getNe_group_id() {
		return ne_group == null ? 0 : ne_group.getNe_group_id();
	}

	public boolean isManage_ne_group() {
		return manage_ne_group;
	}

	public void setManage_ne_group(boolean manage_ne_group) {
		this.manage_ne_group = manage_ne_group;
	}

	public List<TreeNode4NeGroup> getChild_list() {
		return child_list;
	}

	public void addChild(TreeNode4NeGroup child) {
		child_list.add(child);
	}

	public TreeNode4NeGroup findNode(int ne_group_id) {
		if (ne_group != null && ne_group.getNe_group_id() == ne_group_id) {
			return this;
		}
		for (TreeNode4NeGroup aaa : child_list) {
			TreeNode4NeGroup child = aaa.findNode(ne_group_id);
			if (child != null) {
				return child;
			}
		}
		return null;
	}

	@Override
	public TreeNode4NeGroup copy() {
		TreeNode4NeGroup model = new TreeNode4NeGroup();
		model.ne_group = ne_group.copy();
		model.manage_ne_group = manage_ne_group;
		for (TreeNode4NeGroup child : child_list) {
			model.child_list.add(child.copy());
		}
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append("").append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("parent_ne_group_id").append(S_DL).append(ne_group == null ? null : ne_group.getParent_ne_group_id()).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group == null ? null : ne_group.getNe_group_id()).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_name").append(S_DL).append(ne_group == null ? null : ne_group.getNe_group_name()).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("manage_ne_group").append(S_DL).append(manage_ne_group).append(S_NL);
		for (TreeNode4NeGroup child : child_list) {
			stringBuilder.append(child.toString(indent + S_TB)).append(S_NL);
		}
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString("");
	}

}
