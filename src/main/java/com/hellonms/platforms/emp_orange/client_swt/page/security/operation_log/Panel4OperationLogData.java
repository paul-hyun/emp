package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelDetail;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTable.PanelTableListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Panel4OperationLogData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4OperationLogData extends Panel {

	public interface Panel4OperationLogDataListenerIf {

		public void queryListOperationLog(int startNo);

		public void saveExcelOperationLog(String path);

	}

	public class Panel4OperationLogDataChildListener implements //
			PanelTableListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryListOperationLog(startNo);
		}

	}

	/**
	 * 테이블 항목의 최대 개수
	 */
	protected int rowCount;

	/**
	 * 리스너
	 */
	protected Panel4OperationLogDataListenerIf listener;

	/**
	 * 테이블 뷰어
	 */
	protected PanelTableIf panelTable;

	/**
	 * 
	 */
	protected ButtonClick buttonRefresh;

	protected ButtonClick buttonExcel;

	/**
	 * 세부사항 뷰어
	 */
	protected PanelDetail panelDetail;

	/**
	 * 현재알람 모델
	 */
	protected Model4OperationLog model4OperationLog;

	public Panel4OperationLogData(Composite parent, int style, int rowCount, Panel4OperationLogDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.OPERATION_LOG));

		this.rowCount = rowCount;
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelContents = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -(20 + 15 * getDetailTitle().length));
		fd_panelContents.right = new FormAttachment(100, -80);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		panelContents.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.OPERATION_LOG, panelContents.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, rowCount, new Panel4OperationLogDataChildListener());
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.OPERATION_LOG));
		panelTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					displayDetail(null);
				} else {
					displayDetail((Model4OperationLog) ((StructuredSelection) event.getSelection()).getFirstElement());
				}
			}
		});

		ButtonClick[] tableButtons = createTableButton(getContentComposite());
		for (int i = 0; i < tableButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(tableButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelContents, 5) : new FormAttachment(tableButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelContents, 0, SWT.TOP) : new FormAttachment(tableButtons[i - 1], 5, SWT.BOTTOM);
			tableButtons[i].setLayoutData(fd_button);
		}

		PanelRound panelDetailForm = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelDetailForm = new FormData();
		fd_panelDetailForm.top = new FormAttachment(panelContents, 5);
		fd_panelDetailForm.bottom = new FormAttachment(100, -5);
		fd_panelDetailForm.right = new FormAttachment(panelContents, 0, SWT.RIGHT);
		fd_panelDetailForm.left = new FormAttachment(0, 5);
		panelDetailForm.setLayoutData(fd_panelDetailForm);
		panelDetailForm.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelDetail = new PanelDetail(panelDetailForm.getContentComposite(), SWT.NONE, getDetailTitle());

		ButtonClick[] detailButtons = createDetailButton(getContentComposite());
		for (int i = 0; i < detailButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(detailButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelDetail, 5) : new FormAttachment(detailButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelDetail, 0, SWT.TOP) : new FormAttachment(detailButtons[i - 1], 5, SWT.BOTTOM);
			detailButtons[i].setLayoutData(fd_button);
		}
	}

	/**
	 * 테이블 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createTableButton(Composite parent) {
		buttonRefresh = new ButtonClick(parent, SWT.NONE);
		buttonRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListOperationLog(panelTable.getStartNo());
			}
		});
		buttonRefresh.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));

		buttonExcel = new ButtonClick(parent);
		buttonExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE);
				fileDialog.setFilterExtensions(new String[] { "*.xlsx" });
				final String file = fileDialog.open();

				if (file != null) {
					listener.saveExcelOperationLog(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	protected ButtonClick[] createDetailButton(Composite parent) {
		return new ButtonClick[0];
	}

	protected void displayDetail(Model4OperationLog model4OperationLog) {
		this.model4OperationLog = model4OperationLog == null ? null : (Model4OperationLog) model4OperationLog.copy();
		if (model4OperationLog != null) {
			panelDetail.setText(getDetailValue(model4OperationLog));
		} else {
			panelDetail.setText();
		}
	}

	/**
	 * 세부사항의 제목을 반환합니다.
	 * 
	 * @return 세부사항의 제목 배열
	 */
	protected String[] getDetailTitle() {
		return new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.TRANSACTION_ID), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SESSION_ID), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_ID), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_IP), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SERVICE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FUNCTION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.RESULT), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION) //
		};
	}

	protected Object[] getDetailValue(Model4OperationLog model4OperationLog) {
		return new Object[] { UtilDate.format(model4OperationLog.getStart_time()), //
				model4OperationLog.getTransaction_id(), //
				model4OperationLog.getUser_session_id(), //
				model4OperationLog.getUser_account(), //
				model4OperationLog.getUser_ip(), //
				model4OperationLog.getOperation_code().getFunction_group(), //
				model4OperationLog.getOperation_code().getFunction(), //
				model4OperationLog.getOperation_code().getOperation(), //
				model4OperationLog.isResult() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SUCCESS) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FAIL, model4OperationLog.getFail_cause()), //
				model4OperationLog.getDescription() //
		};
	}

	/**
	 * 시작번호를 반환합니다.
	 * 
	 * @return 시작번호
	 */
	public int getStartNo() {
		return panelTable.getStartNo();
	}

	/**
	 * 테이블에 현재알람 리스트를 출력합니다.
	 * 
	 * @param pageConfig
	 *            테이블 페이지 정보
	 */
	public void display(TablePageConfig<Model4OperationLog> pageConfig) {
		panelTable.setDatas((Object) pageConfig.values);
		panelTable.display(pageConfig);
	}

}
