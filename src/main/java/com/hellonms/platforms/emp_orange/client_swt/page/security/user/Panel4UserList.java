package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import java.util.ArrayList;
import java.util.List;

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

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TABLE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * Panel4UserList
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4UserList extends Panel {

	/**
	 * 사용자 목록 판넬의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Panel4UserListListenerIf {

		/**
		 * 
		 */
		public void createUser();

		/**
		 * 사용자 목록을 조회합니다.
		 *
		 * @param startNo
		 *            시작번호
		 */
		public void queryListUser(int startNo);

		/**
		 * 사용자를 선택합니다.
		 *
		 * @param user
		 *            사용자 모델
		 */
		public void selected(Model4User user);

	}

	/**
	 * 테이블 페이지당 최대 항목 개수
	 */
	protected int rowCount;

	/**
	 * 리스너
	 */
	protected Panel4UserListListenerIf listener;

	/**
	 * 테이블뷰어
	 */
	protected PanelTableIf panelTable;

	/**
	 * 테이블뷰어의 버튼 배열
	 */
	protected ButtonClick[] tableButtons;

	/**
	 * 사용자그룹 모델 배열
	 */
	protected ModelDisplay4User displayModel4User;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 */
	public Panel4UserList(Composite parent, int style, int rowCount, Panel4UserListListenerIf listener) {
		super(parent, style, UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_LIST));
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
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -80);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);
		panelContents.getContentComposite().setLayout(new FillLayout(SWT.HORIZONTAL));

		panelTable = PanelFactory.createPanelTable(TABLE_ORANGE.USER, panelContents.getContentComposite(), SWT.BORDER | SWT.FULL_SELECTION, rowCount, null);
		panelTable.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					listener.selected((Model4User) selection.getFirstElement());
				}
			}
		});

		tableButtons = createTableButton(getContentComposite());
		for (int i = 0; i < tableButtons.length; i++) {
			FormData fd_button = new FormData();
			fd_button.right = (i == 0) ? new FormAttachment(100, -5) : new FormAttachment(tableButtons[i - 1], 0, SWT.RIGHT);
			fd_button.left = (i == 0) ? new FormAttachment(panelContents, 5) : new FormAttachment(tableButtons[i - 1], 0, SWT.LEFT);
			fd_button.top = (i == 0) ? new FormAttachment(panelContents, 0, SWT.TOP) : new FormAttachment(tableButtons[i - 1], 5, SWT.BOTTOM);
			tableButtons[i].setLayoutData(fd_button);
		}
	}

	/**
	 * 테이블뷰어의 버튼을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 버튼 배열
	 */
	protected ButtonClick[] createTableButton(Composite parent) {
		List<ButtonClick> buttonList = new ArrayList<ButtonClick>();

		ButtonClick buttonRefresh = new ButtonClick(parent, SWT.NONE);
		buttonRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.queryListUser(panelTable.getStartNo());
			}
		});
		buttonRefresh.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.REFRESH));
		buttonList.add(buttonRefresh);

		if (ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.SECURITY_USER_CREATE)) {
			ButtonClick buttonCreate = new ButtonClick(parent, SWT.NONE);
			buttonCreate.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					listener.createUser();
				}
			});
			buttonCreate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE));
			buttonList.add(buttonCreate);
		}

		return buttonList.toArray(new ButtonClick[0]);
	}

	/**
	 * 화면에 출력합니다.
	 * 
	 * @param displayModel4User
	 *            사용자 표시모델
	 */
	public void display(ModelDisplay4User displayModel4User) {
		this.displayModel4User = displayModel4User;

		panelTable.setDatas((Object) displayModel4User.getTablePageConfig4User().values);
		panelTable.display(displayModel4User.getTablePageConfig4User());
	}

	/**
	 * 시작번호를 반환합니다.
	 * 
	 * @return 시작번호
	 */
	public int getStartNo() {
		return panelTable.getStartNo();
	}

	public ModelDisplay4User getModelDisplay4User() {
		return displayModel4User;
	}

}
