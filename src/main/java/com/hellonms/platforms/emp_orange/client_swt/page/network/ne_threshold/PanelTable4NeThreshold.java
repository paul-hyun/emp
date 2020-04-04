package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * PanelInput4NeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelTable4NeThreshold extends PanelTable implements PanelInput4NeThresholdIf {

	public PanelTable4NeThreshold(Composite parent, int style, int rowCount, PanelTableListenerIf listener, EMP_MODEL_NE_INFO ne_info_def, Object[] datas) {
		super(parent, style, rowCount, listener);
	}

	public void display(Model4NeThresholdIf ne_threshold) {
		getDataTable().setDatas(ne_threshold);
		getTableViewer().setSelection(null);
	}

	@Override
	public void clear() {
		getDataTable().clear();
		this.refresh();
	}

	@Override
	public Model4NeThresholdIf getSelected() {
		return (Model4NeThresholdIf) getDataTable().getData();
	}

	@Override
	public EMP_MODEL_NE_INFO_FIELD getNe_field_code() {
		IStructuredSelection selection = (IStructuredSelection) super.getSelection();
		return (EMP_MODEL_NE_INFO_FIELD) selection.getFirstElement();
	}

	@Override
	public boolean isNeedWizard() {
		return true;
	}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}
