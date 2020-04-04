package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;

/**
 * <p>
 * PanelInput4NeInfo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelTable4NeInfo extends PanelTable implements PanelInput4NeInfoIf {

	public PanelTable4NeInfo(Composite parent, int style, int rowCount, PanelTableListenerIf listener, EMP_MODEL_NE_INFO ne_info_def, Object[] datas) {
		super(parent, style, rowCount, listener);

		this.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent e) {
			}
		});
	}

	public void display(ModelDisplay4NeInfo modelDisplay4NeInfo) {
		getDataTable().setDatas(modelDisplay4NeInfo);
	}

	@Override
	public void clear() {
		getDataTable().clear();
		this.refresh();
	}

	@Override
	public Model4NeInfoIf getSelected() {
		IStructuredSelection selection = (IStructuredSelection) super.getSelection();
		return (Model4NeInfoIf) selection.getFirstElement();
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
