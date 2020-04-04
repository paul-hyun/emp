package com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.ModelClient4NetworkTreeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * DataTree4NetworkTree
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTree4NetworkTree extends DataTreeAt implements ModelClient4NetworkTreeListenerIf {

	/**
	 * 검색단어
	 */
	protected String search_string = "";

	/**
	 * NE 그룹 아이디 필터
	 */
	protected Set<Integer> neGroupIdFilter = new HashSet<Integer>();

	/**
	 * NE 아이디 필터
	 */
	protected Set<Integer> neIdFilter = new HashSet<Integer>();

	/**
	 * 생성자 입니다.
	 */
	public DataTree4NetworkTree() {
		ModelClient4NetworkTree.getInstance().addModelClient4NetworkTreeListener(this);
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
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
					if (node.isNe() && node.getId() == Model4Ne.NMS_NE_ID) {
						nodeList.add(node);
					}
				}
				for (NODE node : nodes) {
					if (node.isNeGroup() && !isFilteredNeGroup(node)) {
						nodeList.add(node);
					} else if (node.isNe() && node.getId() != Model4Ne.NMS_NE_ID && !isFilteredNe(node)) {
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

	/**
	 * 검색단어를 반환합니다.
	 * 
	 * @return 검색단어
	 */
	public String getSearch_string() {
		return search_string;
	}

	/**
	 * 검색단어를 설정합니다.
	 * 
	 * @param ne_search_string
	 *            검색단어
	 */
	public void setSearch_string(String ne_search_string) {
		this.search_string = ne_search_string;
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
	 * NE 아이디 필터를 설정합니다.
	 * 
	 * @param ne_id_filters
	 *            NE 아이디 필터
	 */
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
