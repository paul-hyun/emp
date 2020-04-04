package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;

/**
 * <p>
 * DataCombo4NeType
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4NeType extends DataComboAt {

	protected Integer[] ITEMS = {};

	public DataCombo4NeType() {
		List<Integer> ne_def_list = new ArrayList<Integer>();
		for (EMP_MODEL_NE ne_def : EMP_MODEL.current().getNes()) {
			if (!ne_def.isNMS()) {
				ne_def_list.add(ne_def.getCode());
			}
		}
		ITEMS = ne_def_list.toArray(new Integer[0]);
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void setDatas(Object... datas) {
	}

	@Override
	public Object getDefaultItem() {
		if (ITEMS != null && 0 < ITEMS.length) {
			return ITEMS[0];
		}
		return null;
	}

	@Override
	public Object[] getElements(Object arg0) {
		return ITEMS;
	}

	@Override
	public Image getImage(Object arg0) {
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Integer) {
			EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe((Integer) element);
			return ne_def == null ? "" : ne_def.getProduct_class();
		} else {
			return element.toString();
		}
	}

}
