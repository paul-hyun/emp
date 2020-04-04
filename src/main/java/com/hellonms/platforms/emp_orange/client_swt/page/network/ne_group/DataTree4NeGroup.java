package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

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

/**
 * <p>
 * DataTree4NeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTree4NeGroup extends DataTreeAt {

	/**
	 * NE 그룹 아이디 필터
	 */
	protected Set<Integer> neGroupIdFilter = new HashSet<Integer>();

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
				NODE[] nodes = ModelClient4NetworkTree.getInstance().getListChildNeGroup(((NODE) parentElement).getId());

				List<NODE> nodeList = new ArrayList<NODE>();
				for (NODE node : nodes) {
					if (node.isNeGroup() && !isFilteredNeGroup(node)) {
						nodeList.add(node);
					}
				}
				return nodeList.toArray(new NODE[0]);
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

	/**
	 * 필터 된 NE 그룹인지 확인한다.
	 * 
	 * @param node
	 *            노드
	 * @return NE 그룹 필터 확인결과
	 */
	public boolean isFilteredNeGroup(NODE node) {
		return neGroupIdFilter.contains(node.getId());
	}

}
