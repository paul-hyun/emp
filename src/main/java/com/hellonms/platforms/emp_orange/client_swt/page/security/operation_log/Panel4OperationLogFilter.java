package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCalendar;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCalendar.PERIOD;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer32;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

/**
 * <p>
 * Panel4OperationLogFilter
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4OperationLogFilter extends Panel {

	public interface Panel4OperationLogFilterListenerIf {

		public void queryListOperationLog(int startNo);

	}

	protected Panel4OperationLogFilterListenerIf listener;

	protected SelectorCalendar selectorCalendarFromDate;

	protected SelectorCalendar selectorCalendarToDate;

	protected SelectorCombo selectorComboService;

	protected SelectorCombo selectorComboFunction;

	protected SelectorCombo selectorComboOperation;

	protected SelectorCombo selectorComboResult;

	protected TextInput4Integer32 textInputSessionId;

	protected TextInput4String textInputUserId;

	protected OPERATION_CODE[] operationCodes;

	protected OPERATION_CODE selectionOperationCode;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */

	public Panel4OperationLogFilter(Composite parent, int style, OPERATION_CODE[] operationCodes, Panel4OperationLogFilterListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FILTER_TITLE, MESSAGE_CODE_ORANGE.OPERATION_LOG));
		this.listener = listener;

		createGUI();

		displayService(operationCodes);
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelContents = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -5);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		panelContents.getContentComposite().setLayout(gridLayout);

		LabelText labelFromDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelFromDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelFromDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FROM_DATE));

		selectorCalendarFromDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarFromDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListOperationLog(0);
			}
		});
		selectorCalendarFromDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelToDate = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelToDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelToDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TO_DATE));

		selectorCalendarToDate = new SelectorCalendar(panelContents.getContentComposite(), SWT.NONE, PERIOD.DATE);
		selectorCalendarToDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListOperationLog(0);
			}
		});
		selectorCalendarToDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelService = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelService.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelService.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SERVICE));

		selectorComboService = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboService.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.OPERATION_LOG_SERVICE));
		selectorComboService.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				displayFunction(getService());
				listener.queryListOperationLog(0);
			}
		});
		selectorComboService.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelFunction = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelFunction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelFunction.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FUNCTION));

		selectorComboFunction = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboFunction.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.OPERATION_LOG_FUNCTION));
		selectorComboFunction.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				displayOperation(getService(), getFunction());
				listener.queryListOperationLog(0);
			}
		});
		selectorComboFunction.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelOperation = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelOperation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelOperation.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION));

		selectorComboOperation = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY);
		selectorComboOperation.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.OPERATION_LOG_OPERATION));
		selectorComboOperation.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListOperationLog(0);
			}
		});
		selectorComboOperation.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelResult = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelResult.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelResult.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.RESULT));

		selectorComboResult = new SelectorCombo(panelContents.getContentComposite(), SWT.READ_ONLY, new DataCombo4Result());
		selectorComboResult.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.OPERATION_LOG_RESULT));
		selectorComboResult.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listener.queryListOperationLog(0);
			}
		});
		selectorComboResult.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelSessionId = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelSessionId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSessionId.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SESSION_ID));

		textInputSessionId = new TextInput4Integer32(panelContents.getContentComposite(), SWT.BORDER);
		textInputSessionId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				listener.queryListOperationLog(0);
			}
		});
		textInputSessionId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelUserId = new LabelText(panelContents.getContentComposite(), SWT.NONE);
		labelUserId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserId.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_ID));

		textInputUserId = new TextInput4String(panelContents.getContentComposite(), SWT.BORDER);
		textInputUserId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				listener.queryListOperationLog(0);
			}
		});
		textInputUserId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	public boolean isOperationForNe() {
		return selectionOperationCode == null ? false : selectionOperationCode.isOperation_for_ne();
	}

	public Date getFromTime() {
		return selectorCalendarFromDate.getStartDate();
	}

	public Date getToTime() {
		return selectorCalendarToDate.getEndDate();
	}

	public String getService() {
		Object selectedItem = selectorComboService.getSelectedItem();
		return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL).equals(String.valueOf(selectedItem)) ? null : (String) selectedItem;
	}

	public String getFunction() {
		Object selectedItem = selectorComboFunction.getSelectedItem();
		return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL).equals(String.valueOf(selectedItem)) ? null : (String) selectedItem;
	}

	public String getOperation() {
		Object selectedItem = selectorComboOperation.getSelectedItem();
		return UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALL).equals(String.valueOf(selectedItem)) ? null : (String) selectedItem;
	}

	public Boolean getResult() {
		Object selectedItem = selectorComboResult.getSelectedItem();
		if (UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SUCCESS).equals(selectedItem)) {
			return true;
		} else if (UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAIL).equals(selectedItem)) {
			return false;
		}
		return null;
	}

	public Integer getSessionId() {
		String sessionId = textInputSessionId.getText().trim();
		return sessionId.length() == 0 ? null : Integer.parseInt(sessionId);
	}

	public String getUserId() {
		String userId = textInputUserId.getText().trim();
		return userId.length() == 0 ? null : userId;
	}

	protected void displayService(OPERATION_CODE[] operationCodes) {
		this.operationCodes = operationCodes;

		selectionOperationCode = null;

		Set<String> serviceSet = new LinkedHashSet<String>();
		for (OPERATION_CODE operationCode : operationCodes) {
			if (!serviceSet.contains(operationCode.getFunction_group())) {
				serviceSet.add(operationCode.getFunction_group());
			}
		}

		String[] services = serviceSet.toArray(new String[0]);
		boolean updateServices = selectorComboService.isNeedUpdate((Object) services);
		if (updateServices) {
			selectorComboService.setFireSelectionChanged(false);
			Object selectedItem = selectorComboService.getSelectedItem();
			selectorComboService.setDatas((Object) services);
			selectorComboService.setSelectedItem(selectorComboService.findElement(selectedItem));
			selectorComboService.setFireSelectionChanged(true);

			displayFunction(String.valueOf(selectedItem));
		}
	}

	protected void displayFunction(String service) {
		selectionOperationCode = null;
		boolean operationForNeState = true;

		Set<String> functionSet = new LinkedHashSet<String>();
		if (service != null) {
			for (OPERATION_CODE operationCode : operationCodes) {
				if (service.equals(operationCode.getFunction_group()) && !functionSet.contains(operationCode.getFunction())) {
					if (operationForNeState) {
						selectionOperationCode = operationCode;
						operationForNeState = operationCode.isOperation_for_ne();
					}
					functionSet.add(operationCode.getFunction());
				}
			}
		}

		String[] functions = functionSet.toArray(new String[0]);
		boolean updateFunctions = selectorComboFunction.isNeedUpdate((Object) functions);
		if (updateFunctions) {
			selectorComboFunction.setFireSelectionChanged(false);
			Object selectedItem = selectorComboFunction.getSelectedItem();
			selectorComboFunction.setDatas((Object) functions);
			selectorComboFunction.setSelectedItem(selectorComboFunction.findElement(selectedItem));
			selectorComboFunction.setFireSelectionChanged(true);

			displayOperation(service, String.valueOf(selectedItem));
		}
	}

	protected void displayOperation(String service, String function) {
		selectionOperationCode = null;
		boolean operationForNeState = true;

		Set<String> operationSet = new LinkedHashSet<String>();
		if (service != null && function != null) {
			for (OPERATION_CODE operationCode : operationCodes) {
				if (service.equals(operationCode.getFunction_group()) && function.equals(operationCode.getFunction()) && !operationSet.contains(operationCode.getOperation())) {
					if (operationForNeState) {
						selectionOperationCode = operationCode;
						operationForNeState = operationCode.isOperation_for_ne();
					}
					operationSet.add(operationCode.getOperation());
				}
			}
		}

		String[] operations = operationSet.toArray(new String[0]);
		boolean updateOperations = selectorComboOperation.isNeedUpdate((Object) operations);
		if (updateOperations) {
			selectorComboOperation.setFireSelectionChanged(false);
			Object selectedItem = selectorComboOperation.getSelectedItem();
			selectorComboOperation.setDatas((Object) operations);
			selectorComboOperation.setSelectedItem(selectorComboOperation.findElement(selectedItem));
			selectorComboOperation.setFireSelectionChanged(true);
		}
	}

}
