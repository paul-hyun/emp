package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4User;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.Panel4UserDetail.Panel4UserDetailListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.Panel4UserList.Panel4UserListListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.security.user.Wizard4CreateUser.Wizard4CreateUserListenerIf;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;

/**
 * <p>
 * Page4User
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4User extends Page {

	public class Page4UserChildListener implements //
			Panel4UserListListenerIf, //
			Panel4UserDetailListenerIf, //
			Wizard4CreateUserListenerIf {

		@Override
		public void createUser() {
			Wizard4CreateUser.open(Page4User.this.getShell(), panel4UserList.getModelDisplay4User(), new Page4UserChildListener());
		}

		@Override
		public void queryListUser(int startNo) {
			Page4User.this.queryListUser(startNo, true);
		}

		@Override
		public void selected(Model4User model4User) {
			panel4UserDetail.display(model4User);
		}

		@Override
		public void updateUser(Model4User model4User) {
			Page4User.this.updateUser(model4User, true);
		}

		@Override
		public void deleteUser(Model4User model4User) {
			Page4User.this.deleteUser(model4User, true);
		}

		@Override
		public void createUser(Wizard4CreateUser wizard, Model4User user) {
			Page4User.this.createUser(wizard, user, true);
		}

	}

	/**
	 * 테이블 페이지당 최대 항목 개수
	 */
	protected final int ROW_COUNT = 20;

	/**
	 * 사용자 목록 판넬
	 */
	protected Panel4UserList panel4UserList;

	/**
	 * 사용자 상세정보 판넬
	 */
	protected Panel4UserDetail panel4UserDetail;

	/**
	 * 어드바이저
	 */
	protected Page4UserAdvisor advisor;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public Page4User(Composite parent, int style) {
		super(parent, style, "", "");

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		panel4UserList = createPanel4UserList(getContentComposite(), SWT.NONE, ROW_COUNT, new Page4UserChildListener());
		FormData fd_panel4UserList = new FormData();
		fd_panel4UserList.right = new FormAttachment(50, -3);
		fd_panel4UserList.bottom = new FormAttachment(100, -5);
		fd_panel4UserList.top = new FormAttachment(0, 5);
		fd_panel4UserList.left = new FormAttachment(0, 5);
		panel4UserList.setLayoutData(fd_panel4UserList);

		panel4UserDetail = createPanel4UserDetail(getContentComposite(), SWT.NONE, new Page4UserChildListener());
		FormData fd_panel4UserDetail = new FormData();
		fd_panel4UserDetail.left = new FormAttachment(panel4UserList, 6);
		fd_panel4UserDetail.right = new FormAttachment(100, -5);
		fd_panel4UserDetail.bottom = new FormAttachment(panel4UserList, 0, SWT.BOTTOM);
		fd_panel4UserDetail.top = new FormAttachment(panel4UserList, 0, SWT.TOP);
		panel4UserDetail.setLayoutData(fd_panel4UserDetail);

		advisor = createPage4UserAdvisor();
	}

	/**
	 * 사용자 목록 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 페이지당 최대 항목 개수
	 * @param listener
	 *            리스너
	 * @return 사용자 목록 판넬
	 */
	protected Panel4UserList createPanel4UserList(Composite parent, int style, int rowCount, Panel4UserListListenerIf listener) {
		return new Panel4UserList(parent, style, rowCount, listener);
	}

	/**
	 * 사용자 상세정보 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 * @return 사용자 상세정보 판넬
	 */
	protected Panel4UserDetail createPanel4UserDetail(Composite parent, int style, Panel4UserDetailListenerIf listener) {
		return new Panel4UserDetail(parent, style, listener);
	}

	/**
	 * 사용자 페이지의 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4UserAdvisor createPage4UserAdvisor() {
		return new Page4UserAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListUser(panel4UserList.getStartNo(), progressBar);
	}

	/**
	 * 사용자를 생성합니다.
	 *
	 * @param wizard
	 *            사용자 생성 위자드
	 * @param user
	 *            사용자 모델
	 * @param progressBar
	 *            프로그래스바 싱행여부
	 */
	protected void createUser(Wizard4CreateUser wizard, final Model4User user, boolean progressBar) {
		try {
			final int startNo = panel4UserList.getStartNo();

			ModelDisplay4User modelDisplay4User = (ModelDisplay4User) DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.createUser(user, startNo, ROW_COUNT);
				}
			});

			panel4UserList.display(modelDisplay4User);
			panel4UserDetail.display(modelDisplay4User);

			Wizard4CreateUser.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CREATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		}
	}

	/**
	 * 사용자 목록을 조회합니다.
	 *
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void queryListUser(final int startNo, boolean progressBar) {
		try {

			ModelDisplay4User modelDisplay4User = (ModelDisplay4User) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListUser(startNo, ROW_COUNT);
				}
			});

			panel4UserList.display(modelDisplay4User);
			panel4UserDetail.display(modelDisplay4User);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		}
	}

	/**
	 * 사용자 등록정보를 수정합니다.
	 *
	 * @param user
	 *            사용자 모델
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void updateUser(final Model4User user, boolean progressBar) {
		try {
			final int startNo = panel4UserList.getStartNo();

			ModelDisplay4User modelDisplay4User = (ModelDisplay4User) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateUser(user, startNo, ROW_COUNT);
				}
			});

			panel4UserList.display(modelDisplay4User);
			panel4UserDetail.display(modelDisplay4User);

			Model4User displayUser = null;
			for (Model4User userTemp : modelDisplay4User.getTablePageConfig4User().values) {
				if (user.getUser_id() == userTemp.getUser_id()) {
					displayUser = userTemp;
					break;
				}
			}
			panel4UserDetail.display(displayUser);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		}
	}

	/**
	 * 사용자를 삭제합니다.
	 *
	 * @param user_id
	 *            사용자 아이디
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void deleteUser(final Model4User model4User, boolean progressBar) {
		try {
			final int startNo = panel4UserList.getStartNo();

			ModelDisplay4User modelDisplay4User = (ModelDisplay4User) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.deleteUser(model4User, startNo, ROW_COUNT);
				}
			});

			panel4UserList.display(modelDisplay4User);
			panel4UserDetail.display(modelDisplay4User);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DELETE_TITLE, MESSAGE_CODE_ORANGE.USER_MANAGEMENT), ex);
		}
	}

}
