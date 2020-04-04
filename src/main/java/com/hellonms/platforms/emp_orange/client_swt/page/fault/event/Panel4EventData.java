package com.hellonms.platforms.emp_orange.client_swt.page.fault.event;

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

import com.hellonms.platforms.emp_core.share.error.EmpException;
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
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * Panel4EventData
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4EventData extends Panel {

	public interface Panel4EventDataListenerIf {

		public void queryListEvent(int startNo);

		public void saveExcelEvent(String path);

	}

	public class Panel4EventDataChildListener implements //
			PanelTableListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.queryListEvent(startNo);
		}

	}

	protected int rowCount;

	protected Panel4EventDataListenerIf listener;

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
	protected PanelDetail detailViewer;

	/**
	 * 현재 event
	 */
	protected Model4Event event;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 항목의 최대 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4EventData(Composite parent, int style, int rowCount, Panel4EventDataListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DATA_TITLE, MESSAGE_CODE_ORANGE.EVENT));
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

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.EVENT, panelContents.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, rowCount, new Panel4EventDataChildListener());
		panelTable.setDataTable(DataFactory.createDataTable(DATA_TABLE_ORANGE.EVENT));
		panelTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					displayDetail(null);
				} else {
					displayDetail((Model4Event) ((StructuredSelection) event.getSelection()).getFirstElement());
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

		PanelRound panelDetail = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelDetail = new FormData();
		fd_panelDetail.top = new FormAttachment(panelContents, 5);
		fd_panelDetail.bottom = new FormAttachment(100, -5);
		fd_panelDetail.right = new FormAttachment(panelContents, 0, SWT.RIGHT);
		fd_panelDetail.left = new FormAttachment(0, 5);
		panelDetail.setLayoutData(fd_panelDetail);
		panelDetail.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		detailViewer = new PanelDetail(panelDetail.getContentComposite(), SWT.NONE, getDetailTitle());

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
				listener.queryListEvent(panelTable.getStartNo());
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
					listener.saveExcelEvent(file);
				}
			}
		});
		buttonExcel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EXCEL));

		return new ButtonClick[] { buttonRefresh, buttonExcel };
	}

	/**
	 * 세부사항 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createDetailButton(Composite parent) {
		return new ButtonClick[] {};
	}

	/**
	 * 세부사항 뷰어를 출력합니다.
	 * 
	 * @param event
	 *            현재알람 모델
	 */
	protected void displayDetail(Model4Event event) {
		this.event = (event == null ? null : event.copy());

		if (event != null) {
			detailViewer.setText(getDetailValue(event));
		} else {
			detailViewer.setText();
		}
	}

	/**
	 * 세부사항의 제목을 반환합니다.
	 * 
	 * @return 세부사항의 제목 배열
	 */
	protected String[] getDetailTitle() {
		return new String[] { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT_SEVERITY), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.GEN_TIME), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.LOCATION), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CAUSE), //
				UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION) //
		};
	}

	/**
	 * 세부사항의 내용을 반환합니다.
	 * 
	 * @param alarmHistory
	 *            현재알람 모델
	 * @return 세부사항의 내용 배열
	 */
	protected Object[] getDetailValue(Model4Event event) {
		String neName = "";
		try {
			neName = ModelClient4NetworkTree.getInstance().getNe(event.getNe_id()).getName();
		} catch (EmpException e) {
		}
		EMP_MODEL_EVENT event_def = EMP_MODEL.current().getEvent(event.getEvent_code());
		return new Object[] { event.getSeverity().name(), //
				UtilDate.format(event.getGen_time()), //
				neName, //
				event.getLocation_display(), //
				event_def == null ? "" : event_def.getSpecific_problem(), //
				event.getDescription(), //
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
	public void display(TablePageConfig<Model4Event> pageConfig) {
		panelTable.setDatas((Object) pageConfig.values);
		panelTable.display(pageConfig);
	}

	/**
	 * 테이블에 현재알람 리스트를 출력합니다.
	 * 
	 * @param pageConfig
	 *            테이블 페이지 정보
	 * @param event_id
	 *            이벤트 발생 아이디
	 */
	public void display(TablePageConfig<Model4Event> pageConfig, long event_id) {
		display(pageConfig);

		Model4Event event = null;
		for (Model4Event aaa : pageConfig.values) {
			if (aaa.getEvent_id() == event_id) {
				event = aaa;
				break;
			}
		}
		displayDetail(event);
	}

}
