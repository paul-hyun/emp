package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.snmp;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboSimple;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPeriod;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer16;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.DataCombo4SnmpVersion;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session.PanelInput4NeSessionAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;
import com.hellonms.platforms.emp_util.date.UtilDate;

public class PanelInput4NeSessionSNMP extends PanelInput4NeSessionAt {

	/**
	 * 사용여부 체크 버튼
	 */
	protected ButtonClick buttonCheckState;

	/**
	 * 포트번호 입력 필드
	 */
	protected TextInput4Integer16 textInputPort;

	/**
	 * 버전 콤보 뷰어
	 */
	protected SelectorCombo selectorComboVersion;

	/**
	 * 커뮤니티 읽기 입력 필드
	 */
	protected TextInput4String textInputReadCommunity;

	/**
	 * 커뮤니티 쓰기 입력 필드
	 */
	protected TextInput4String textInputWriteCommunity;

	/**
	 * CHARECTER SET 콤보 뷰어
	 */
	protected SelectorCombo selectorComboCharset;

	/**
	 * 타임아웃 콤보 뷰어
	 */
	protected SelectorCombo selectorComboTimeout;

	/**
	 * 기간 셀렉터
	 */
	protected SelectorPeriod selectorPeriod;

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
	public PanelInput4NeSessionSNMP(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new GridLayout(3, false));

		buttonCheckState = new ButtonClick(getContentComposite(), SWT.CHECK);
		buttonCheckState.setSelection(true);
		buttonCheckState.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}

		});
		buttonCheckState.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP));
		new Label(getContentComposite(), SWT.NONE);
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelPort = new LabelText(getContentComposite(), SWT.NONE);
		labelPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelPort.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PORT));

		textInputPort = new TextInput4Integer16(getContentComposite(), SWT.BORDER);
		textInputPort.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelVersion = new LabelText(getContentComposite(), SWT.NONE);
		labelVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelVersion.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP_VERSION));

		selectorComboVersion = new SelectorCombo(getContentComposite(), SWT.READ_ONLY, new DataCombo4SnmpVersion());
		selectorComboVersion.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_SESSION_SNMP_VERSION));
		selectorComboVersion.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkComplete();
			}
		});
		selectorComboVersion.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelReadCommunity = new LabelText(getContentComposite(), SWT.NONE);
		labelReadCommunity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelReadCommunity.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.READ_COMMUNITY));

		textInputReadCommunity = new TextInput4String(getContentComposite(), SWT.BORDER | SWT.PASSWORD);
		textInputReadCommunity.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputReadCommunity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelWriteCommunity = new LabelText(getContentComposite(), SWT.NONE);
		labelWriteCommunity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelWriteCommunity.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.WRITE_COMMUNITY));

		textInputWriteCommunity = new TextInput4String(getContentComposite(), SWT.BORDER | SWT.PASSWORD);
		textInputWriteCommunity.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputWriteCommunity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelCharset = new LabelText(getContentComposite(), SWT.NONE);
		labelCharset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelCharset.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHARACTER_SET));

		selectorComboCharset = new SelectorCombo(getContentComposite(), SWT.READ_ONLY);
		selectorComboCharset.setDataCombo(new DataComboSimple("", "UTF-8", "ISO-8859-1", "EUC-KR"));
		selectorComboCharset.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkComplete();
			}
		});
		selectorComboCharset.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);

		LabelText labelTimeout = new LabelText(getContentComposite(), SWT.NONE);
		labelTimeout.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelTimeout.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TIMEOUT));

		selectorComboTimeout = new SelectorCombo(getContentComposite(), SWT.READ_ONLY);
		selectorComboTimeout.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_SESSION_TIMEOUT));
		selectorComboTimeout.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				checkComplete();
			}
		});
		selectorComboTimeout.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelSecond = new LabelText(getContentComposite(), SWT.NONE);
		labelSecond.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SECS));

		LabelText labelSessionCheckPeriod = new LabelText(getContentComposite(), SWT.NONE);
		labelSessionCheckPeriod.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSessionCheckPeriod.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PERIOD));

		selectorPeriod = new SelectorPeriod(getContentComposite(), SWT.NONE, UtilDate.HOUR * 1, UtilDate.MINUTE * 1);
		selectorPeriod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}
		});
		selectorPeriod.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(getContentComposite(), SWT.NONE);
	}

	@Override
	protected void checkComplete() {
		textInputPort.setEnabled(buttonCheckState.getSelection());
		selectorComboVersion.getControl().setEnabled(buttonCheckState.getSelection());
		textInputReadCommunity.setEnabled(buttonCheckState.getSelection());
		textInputWriteCommunity.setEnabled(buttonCheckState.getSelection());
		selectorComboCharset.getControl().setEnabled(buttonCheckState.getSelection());
		selectorComboTimeout.getControl().setEnabled(buttonCheckState.getSelection());
		selectorPeriod.setEnabled(buttonCheckState.getSelection());

		if (buttonCheckState.getSelection() && textInputPort.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.PORT));
			setComplete(false);
		} else if (buttonCheckState.getSelection() && selectorComboVersion.getSelection().isEmpty()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.SNMP_VERSION));
			setComplete(false);
		} else if (buttonCheckState.getSelection() && textInputReadCommunity.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.READ_COMMUNITY));
			setComplete(false);
		} else if (buttonCheckState.getSelection() && textInputWriteCommunity.getText().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.WRITE_COMMUNITY));
			setComplete(false);
		} else if (buttonCheckState.getSelection() && selectorComboTimeout.getSelection().isEmpty()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.TIMEOUT));
			setComplete(false);
		} else if (buttonCheckState.getSelection() && selectorPeriod.getPeriod() == 0) {
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
		if (model instanceof Model4NeSessionSNMP) {
			this.model = ((Model4NeSessionSNMP) model).copy();
			displayModel();
		}
	}

	protected void displayModel() {
		if (model != null) {
			Model4NeSessionSNMP snmp = (Model4NeSessionSNMP) model;
			buttonCheckState.setSelection(snmp.isAdmin_state());
			textInputPort.setText(String.valueOf(snmp.getPort()));
			selectorComboVersion.setSelectedItem(snmp.getVersion());
			textInputReadCommunity.setText(snmp.getRead_community());
			textInputWriteCommunity.setText(snmp.getWrite_community());
			selectorComboCharset.setSelectedItem(snmp.getCharset());
			selectorComboTimeout.setSelectedItem((int) snmp.getTimeout() / 1000);
			selectorPeriod.setPeriod(snmp.getSession_check_period() * 1000);

			checkComplete();
		}
	}

	protected void applyModel() {
		if (model != null) {
			Model4NeSessionSNMP snmp = (Model4NeSessionSNMP) model;
			snmp.setAdmin_state(buttonCheckState.getSelection());
			snmp.setPort(Integer.parseInt(textInputPort.getText().trim()));
			snmp.setVersion((SNMP_VERSION) selectorComboVersion.getSelectedItem());
			snmp.setRead_community(textInputReadCommunity.getText());
			snmp.setWrite_community(textInputWriteCommunity.getText());
			snmp.setCharset((String) selectorComboCharset.getSelectedItem());
			snmp.setTimeout(((Integer) selectorComboTimeout.getSelectedItem()) * 1000);
			snmp.setSession_check_period((int) (selectorPeriod.getPeriod() / 1000));
		}
	}

}
