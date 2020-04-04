package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import java.io.Serializable;

import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;

public interface PanelFieldIf {

	public Control getControl();

	public boolean isComplete();

	public boolean isChanged();

	public EMP_MODEL_NE_INFO getNeInfoDef();

	public EMP_MODEL_NE_INFO_FIELD getNeFieldDef();

	public String getFieldName();

	public Serializable getNeFieldValue();

	public void setNeFieldValue(Serializable neFieldValue);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

}
