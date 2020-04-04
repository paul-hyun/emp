package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * DataTree4ManageNeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTree4ManageNeGroup extends DataTreeAt {

	private Map<Integer, Model4NeGroup> model4NeGroupMap = new HashMap<Integer, Model4NeGroup>();

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
			Model4NeGroup rootNeGroup = model4NeGroupMap.get(Model4NeGroup.ROOT_NE_GROUP_ID);
			if (rootNeGroup != null) {
				return new Object[] { rootNeGroup };
			} else {
				return new Object[] {};
			}
		} else if (parentElement instanceof Model4NeGroup) {
			Model4NeGroup parentNeGroup = (Model4NeGroup) parentElement;
			List<Model4NeGroup> model4NeGroupList = new ArrayList<Model4NeGroup>();
			for (Model4NeGroup model4NeGroup : model4NeGroupMap.values()) {
				if (parentNeGroup.getNe_group_id() == model4NeGroup.getParent_ne_group_id() && model4NeGroup.getNe_group_id() != Model4NeGroup.ROOT_NE_GROUP_ID) {
					model4NeGroupList.add(model4NeGroup);
				}
			}
			return model4NeGroupList.toArray(new Model4NeGroup[0]);
		} else {
			return new Object[] {};
		}
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Model4NeGroup) {
			Model4NeGroup model4NeGroup = (Model4NeGroup) element;
			int parent_ne_group_id = model4NeGroup.getParent_ne_group_id();
			return model4NeGroupMap.get(parent_ne_group_id);
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
			return UtilResource4Orange.getNetworkTreeIcon(((NODE) element).getIcon());
		} else {
			return null;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Model4NeGroup) {
			return ((Model4NeGroup) element).getNe_group_name();
		} else {
			return element.toString();
		}
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas != null && datas.length == 1 && datas[0] instanceof Model4NeGroup[]) {
			setData((Model4NeGroup[]) datas[0]);
		}
	}

	private void setData(Model4NeGroup[] model4NeGroups) {
		model4NeGroupMap.clear();
		for (Model4NeGroup model4NeGroup : model4NeGroups) {
			model4NeGroupMap.put(model4NeGroup.getNe_group_id(), model4NeGroup);
		}
	}

	public Model4NeGroup getNeGroup(int ne_group_id) {
		return model4NeGroupMap.get(ne_group_id);
	}

}
