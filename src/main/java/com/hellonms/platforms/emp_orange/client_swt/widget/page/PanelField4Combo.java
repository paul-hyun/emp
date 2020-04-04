package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.DataCombo4NeFieldEnum;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public class PanelField4Combo extends PanelFieldAt {

	protected SelectorCombo selectorNeFieldEnum;

	public PanelField4Combo(Composite parent, EMP_MODEL_NE_INFO ne_info_def, EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean enabled, PanelFieldListenerIf listener) {
		super(parent, ne_info_def, ne_info_field_def, enabled, listener);
	}

	@Override
	protected Control createControl(Composite parent, int style) {
		selectorNeFieldEnum = new SelectorCombo(parent, SWT.READ_ONLY | style, new DataCombo4NeFieldEnum());
		EMP_MODEL_ENUM enum_def = EMP_MODEL.current().getEnum(ne_info_field_def.getEnum_code());
		selectorNeFieldEnum.setDatas((Object) (enum_def == null ? new EMP_MODEL_ENUM_FIELD[0] : enum_def.getEnum_fields()));
		selectorNeFieldEnum.setSelectedItem(selectorNeFieldEnum.getDefaultItem());
		selectorNeFieldEnum.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.checkComplete();
			}
		});
		selectorNeFieldEnum.setSelectedItem(neFieldValue == null ? selectorNeFieldEnum.getDefaultItem() : neFieldValue);
		selectorNeFieldEnum.getControl().setEnabled(enabled);

		return selectorNeFieldEnum.getControl();
	}

	@Override
	public boolean isComplete() {
		if (!enabled) {
			return true;
		}
		return selectorNeFieldEnum.isComplete() && selectorNeFieldEnum.getSelectedItem() != null;
	}

	@Override
	public Serializable getNeFieldValue() {
		EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selectorNeFieldEnum.getSelectedItem();
		return enum_field == null ? null : enum_field.getValue();
	}

	@Override
	public void setNeFieldValue(Serializable neFieldValue) {
		this.neFieldValue = neFieldValue;
		if (neFieldValue != null) {
			EMP_MODEL_ENUM enum_def = EMP_MODEL.current().getEnum(ne_info_field_def.getEnum_code());
			EMP_MODEL_ENUM_FIELD enum_field = (EMP_MODEL_ENUM_FIELD) selectorNeFieldEnum.getDefaultItem();
			if (enum_def != null) {
				for (EMP_MODEL_ENUM_FIELD aaa : enum_def.getEnum_fields()) {
					if (aaa.getValue().equals(String.valueOf(neFieldValue))) {
						enum_field = aaa;
						break;
					}
				}
			}
			selectorNeFieldEnum.setSelectedItem(enum_field);
		} else {
			selectorNeFieldEnum.setSelectedItem(selectorNeFieldEnum.getDefaultItem());
		}
	}
}
