package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

public class DataTree4Ne extends DataTreeAt {

	/**
	 * NE 그룹 아이디 필터
	 */
	protected Set<Integer> neGroupIdFilter = new HashSet<Integer>();

	/**
	 * NE 아이디 필터
	 */
	protected Set<Integer> neIdFilter = new HashSet<Integer>();

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (this == parentElement) {
			try {
				return new Object[] { ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID) };
			} catch (EmpException e) {
				e.printStackTrace();
				return new Object[] {};
			}
		} else if (parentElement instanceof NODE && ((NODE) parentElement).isNeGroup()) {
			try {
				NODE[] nodes = ModelClient4NetworkTree.getInstance().getListChild(((NODE) parentElement).getId());

				List<NODE> nodeList = new ArrayList<NODE>();
				for (NODE node : nodes) {
					if (node.isNeGroup() && !isFilteredNeGroup(node)) {
						nodeList.add(node);
					} else if (node.isNe() && !isFilteredNe(node)) {
						nodeList.add(node);
					}
				}
				return nodeList.toArray();
			} catch (EmpException e) {
				e.printStackTrace();
				return new Object[] {};
			}
		} else {
			return new Object[] {};
		}
	}

	@Override
	public Object getParent(Object element) {
		if (this == element) {
			return null;
		} else if (element instanceof NODE) {
			return ModelClient4NetworkTree.getInstance().getParent((NODE) element);
		} else {
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		return (getChildren(element).length > 0);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof NODE) {
			return UtilResource4Orange.getNetworkTreeIcon(((NODE) element).getIcon(), ((NODE) element).getSeverity());
		} else {
			return null;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof NODE) {
			return ((NODE) element).getName();
		} else {
			return element.toString();
		}
	}

	/**
	 * 첫 번째 NE를 반환합니다.
	 * 
	 * @return NE
	 */
	public NODE getFirstNe() {
		try {
			return ModelClient4NetworkTree.getInstance().getFirstNe(neGroupIdFilter, neIdFilter);
		} catch (EmpException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * NE를 반환합니다.
	 * 
	 * @param ne_id
	 *            NE 아이디
	 * @return NE
	 */
	public NODE getNe(int ne_id) {
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNe(ne_id);
			if (isFilteredNe(node)) {
				return null;
			} else {
				return node;
			}
		} catch (EmpException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * NE 그룹 아이디 필터를 설정합니다.
	 * 
	 * @param ne_group_id_filters
	 *            NE 그룹 아이디 필터
	 */
	public void setNe_group_id_filters(int[] ne_group_id_filters) {
		neGroupIdFilter.clear();
		for (int ne_group_id : ne_group_id_filters) {
			neGroupIdFilter.add(ne_group_id);
		}
	}

	public void setNe_id_filters(int[] ne_id_filters) {
		neIdFilter.clear();
		for (int ne_id : ne_id_filters) {
			neIdFilter.add(ne_id);
		}
	}

	/**
	 * 필터된 NE 그룹인지 확인합니다.
	 * 
	 * @param node
	 *            노드
	 * @return 필터 확인결과
	 */
	public boolean isFilteredNeGroup(NODE node) {
		return neGroupIdFilter.contains(node.getId());
	}

	/**
	 * 필터된 NE인지 확인합니다.
	 * 
	 * @param node
	 *            노드
	 * @return 필터 확인결과
	 */
	public boolean isFilteredNe(NODE node) {
		return neIdFilter.contains(node.getId());
	}

}
