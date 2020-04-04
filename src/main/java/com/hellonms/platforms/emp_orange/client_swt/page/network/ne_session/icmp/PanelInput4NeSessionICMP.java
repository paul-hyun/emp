package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.icmp;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPeriod;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.DataCombo4Timeout;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.PanelInput4NeSessionAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_util.date.UtilDate;

public class PanelInput4NeSessionICMP extends PanelInput4NeSessionAt {

	/**
	 * 사용여부 체크 버튼
	 */
	protected ButtonClick checkState;

	/**
	 * 타임아웃 콤보 모델
	 */
	protected SelectorCombo selectorTimeout;

	/**
	 * 기간 셀렉터
	 */
	protected SelectorPeriod periodSelector;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            입력 판넬 리스너
	 */
	public PanelInput4NeSessionICMP(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new GridLayout(3, false));

		checkState = new ButtonClick(getContentComposite(), SWT.CHECK);
		checkState.setSelection(true);
		checkState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}

		});
		checkState.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ICMP));
		new Label(getContentComposite(), SWT.NONE);
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelTimeout = new LabelText(getContentComposite(), SWT.NONE);
		labelTimeout.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTimeout.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TIMEOUT));

		selectorTimeout = new SelectorCombo(getContentComposite(), SWT.READ_ONLY, new DataCombo4Timeout(10));
		selectorTimeout.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkComplete();
			}
		});
		selectorTimeout.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelSecond = new LabelText(getContentComposite(), SWT.NONE);
		labelSecond.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SECS));

		LabelText labelSessionCheckPeriod = new LabelText(getContentComposite(), SWT.NONE);
		labelSessionCheckPeriod.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSessionCheckPeriod.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PERIOD));

		periodSelector = new SelectorPeriod(getContentComposite(), SWT.NONE, UtilDate.HOUR * 1, UtilDate.SECOND * 5);
		periodSelector.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}
		});
		periodSelector.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);
	}

	@Override
	protected void checkComplete() {
		selectorTimeout.getControl().setEnabled(checkState.getSelection());
		periodSelector.setEnabled(checkState.getSelection());

		if (checkState.getSelection() && selectorTimeout.getSelection().isEmpty()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.TIMEOUT));
			setComplete(false);
		} else if (checkState.getSelection() && periodSelector.getPeriod() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.PERIOD));
			setComplete(false);
		} else {
			setErrorMessage(null);
			setComplete(true);
		}

		completeChanged();
	}

	@Override
	public Model4NeSessionIf getModel() {
		applyModel();
		return model;
	}

	@Override
	public void setModel(Model4NeSessionIf model) {
		if (model instanceof Model4NeSessionICMP) {
			this.model = (Model4NeSessionICMP) model.copy();
			displayModel();
		}
	}

	protected void displayModel() {
		if (model != null) {
			Model4NeSessionICMP icmp = (Model4NeSessionICMP) model;
			checkState.setSelection(icmp.isAdmin_state());
			selectorTimeout.setSelectedItem((int) icmp.getTimeout() / 1000);
			periodSelector.setPeriod(icmp.getSession_check_period() * 1000);

			checkComplete();
		}
	}

	protected void applyModel() {
		if (model != null) {
			Model4NeSessionICMP icmp = (Model4NeSessionICMP) model;
			icmp.setAdmin_state(checkState.getSelection());
			icmp.setTimeout(((Integer) selectorTimeout.getSelectedItem()) * 1000);
			icmp.setSession_check_period((int) (periodSelector.getPeriod() / 1000));
		}
	}

}
