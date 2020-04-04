package com.hellonms.platforms.emp_orange.client_swt.page.fault.event;

import java.io.File;
import java.util.Date;

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
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.Panel4EventData.Panel4EventDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.event.Panel4EventFilter.Panel4EventFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4Event
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4Event extends PageNode {

	/**
	 * 현재알람의 검색 판넬과 검색결과 판넬의 리스너 인터페이스를 구현한 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4EventChildListener implements //
			Panel4EventFilterListenerIf, //
			Panel4EventDataListenerIf {

		@Override
		public void queryListEvent(int startNo) {
			Page4Event.this.queryListEvent(startNo, true);
		}

		@Override
		public void saveExcelEvent(String path) {
			Page4Event.this.saveExcelEvent(path, true);
		}

	}

	/**
	 * 현재알람 검색결과 판넬의 테이블 항목의 최대 개수
	 */
	protected final int ROW_COUNT = 20;

	/**
	 * 현재알람 검색 판넬
	 */
	protected Panel4EventFilter panel4EventFilter;

	/**
	 * 현재알람 검색결과 판넬
	 */
	protected Panel4EventData panel4EventData;

	/**
	 * 현재알람 페이지의 어드바이저
	 */
	protected Page4EventAdvisor advisor;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param view
	 *            뷰
	 */
	public Page4Event(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, listener);

		advisor = createPage4EventAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4EventFilter = createPanel4EventFilter(contentComposite, SWT.NONE, new Page4EventChildListener());
		FormData fd_panel4EventFilter = new FormData();
		fd_panel4EventFilter.right = new FormAttachment(0, 245);
		fd_panel4EventFilter.bottom = new FormAttachment(100, -5);
		fd_panel4EventFilter.top = new FormAttachment(0, 5);
		fd_panel4EventFilter.left = new FormAttachment(0, 5);
		panel4EventFilter.setLayoutData(fd_panel4EventFilter);

		panel4EventData = createPanel4EventData(contentComposite, SWT.NONE, ROW_COUNT, new Page4EventChildListener());
		FormData fd_panel4EventData = new FormData();
		fd_panel4EventData.left = new FormAttachment(panel4EventFilter, 6);
		fd_panel4EventData.right = new FormAttachment(100, -5);
		fd_panel4EventData.bottom = new FormAttachment(panel4EventFilter, 0, SWT.BOTTOM);
		fd_panel4EventData.top = new FormAttachment(panel4EventFilter, 0, SWT.TOP);
		panel4EventData.setLayoutData(fd_panel4EventData);

		return contentComposite;
	}

	/**
	 * 현재알람 검색 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            현재알람 검색 판넬의 리스너
	 * @return 현재알람 검색 판넬
	 */
	protected Panel4EventFilter createPanel4EventFilter(Composite parent, int style, Panel4EventFilterListenerIf listener) {
		return new Panel4EventFilter(parent, style, listener);
	}

	/**
	 * 현재알람 검색결과 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            테이블 항목의 최대 개수
	 * @param listener
	 *            현재알람 검색결과 판넬의 리스너
	 * @return 현재알람 검색결과 판넬
	 */
	protected Panel4EventData createPanel4EventData(Composite parent, int style, int rowCount, Panel4EventDataListenerIf listener) {
		return new Panel4EventData(parent, style, rowCount, listener);
	}

	/**
	 * 현재알람 페이지의 어드바이저를 생성합니다.
	 * 
	 * @return 현재알람 페이지의 어드바이저
	 */
	protected Page4EventAdvisor createPage4EventAdvisor() {
		return new Page4EventAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListEvent(panel4EventData.getStartNo(), progressBar);
	}

	/**
	 * 선택된 노드의 현재알람을 조회하고 결과를 화면에 출력합니다.
	 * 
	 * @param node
	 *            노드
	 */
	@Override
	public void display(NODE node) {
		super.display(node);
		if (node.isNeGroup()) {
			panel4EventFilter.setSeverity(null);
			queryListEvent(0, true);
		} else if (node.isNe()) {
			panel4EventFilter.setSeverity(null);
			queryListEvent(0, true);
		}
	}

	/**
	 * @param severity
	 */
	public void display(SEVERITY severity) {
		panel4EventFilter.setSeverity(severity);
		queryListEvent(0, true);
	}

	/**
	 * 현재알람 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행상태
	 */
	@SuppressWarnings("unchecked")
	protected void queryListEvent(final int startNo, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final EMP_MODEL_EVENT event_def = panel4EventFilter.getEventCode();
			final SEVERITY severity = panel4EventFilter.getSeverity();
			final Date fromDate = panel4EventFilter.getFromDate();
			final Date toDate = panel4EventFilter.getToDate();

			TablePageConfig<Model4Event> pageConfig = (TablePageConfig<Model4Event>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListEvent(node, event_def, severity, fromDate, toDate, startNo, ROW_COUNT);
				}
			});

			panel4EventData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), ex);
		}
	}

	protected void saveExcelEvent(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final EMP_MODEL_EVENT event_def = panel4EventFilter.getEventCode();
			final SEVERITY severity = panel4EventFilter.getSeverity();
			final Date fromDate = panel4EventFilter.getFromDate();
			final Date toDate = panel4EventFilter.getToDate();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelEvent(node, event_def, severity, fromDate, toDate);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.EVENT), ex);
		}
	}

}
