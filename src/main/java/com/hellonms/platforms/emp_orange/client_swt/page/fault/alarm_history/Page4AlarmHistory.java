package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history;

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
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.Panel4AlarmHistoryData.Panel4AlarmHistoryDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_history.Panel4AlarmHistoryFilter.Panel4AlarmHistoryFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_EVENT;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4AlarmHistory
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmHistory extends PageNode {

	/**
	 * 현재알람의 검색 판넬과 검색결과 판넬의 리스너 인터페이스를 구현한 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4AlarmHistoryChildListener implements //
			Panel4AlarmHistoryFilterListenerIf, //
			Panel4AlarmHistoryDataListenerIf {

		@Override
		public void queryListAlarmHistory(int startNo) {
			Page4AlarmHistory.this.queryListAlarmHistory(startNo, true);
		}

		@Override
		public void saveExcelAlarmHistory(String path) {
			Page4AlarmHistory.this.saveExcelAlarmHistory(path, true);
		}

	}

	/**
	 * 현재알람 검색결과 판넬의 테이블 항목의 최대 개수
	 */
	protected final int ROW_COUNT = 20;

	/**
	 * 현재알람 검색 판넬
	 */
	protected Panel4AlarmHistoryFilter panel4AlarmHistoryFilter;

	/**
	 * 현재알람 검색결과 판넬
	 */
	protected Panel4AlarmHistoryData panel4AlarmHistoryData;

	/**
	 * 현재알람 페이지의 어드바이저
	 */
	protected Page4AlarmHistoryAdvisor advisor;

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
	public Page4AlarmHistory(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, listener);

		advisor = createPage4AlarmHistoryAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4AlarmHistoryFilter = createPanel4AlarmHistoryFilter(contentComposite, SWT.NONE, new Page4AlarmHistoryChildListener());
		FormData fd_panel4AlarmHistoryFilter = new FormData();
		fd_panel4AlarmHistoryFilter.right = new FormAttachment(0, 245);
		fd_panel4AlarmHistoryFilter.bottom = new FormAttachment(100, -5);
		fd_panel4AlarmHistoryFilter.top = new FormAttachment(0, 5);
		fd_panel4AlarmHistoryFilter.left = new FormAttachment(0, 5);
		panel4AlarmHistoryFilter.setLayoutData(fd_panel4AlarmHistoryFilter);

		panel4AlarmHistoryData = createPanel4AlarmHistoryData(contentComposite, SWT.NONE, ROW_COUNT, new Page4AlarmHistoryChildListener());
		FormData fd_panel4AlarmHistoryData = new FormData();
		fd_panel4AlarmHistoryData.left = new FormAttachment(panel4AlarmHistoryFilter, 6);
		fd_panel4AlarmHistoryData.right = new FormAttachment(100, -5);
		fd_panel4AlarmHistoryData.bottom = new FormAttachment(panel4AlarmHistoryFilter, 0, SWT.BOTTOM);
		fd_panel4AlarmHistoryData.top = new FormAttachment(panel4AlarmHistoryFilter, 0, SWT.TOP);
		panel4AlarmHistoryData.setLayoutData(fd_panel4AlarmHistoryData);

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
	protected Panel4AlarmHistoryFilter createPanel4AlarmHistoryFilter(Composite parent, int style, Panel4AlarmHistoryFilterListenerIf listener) {
		return new Panel4AlarmHistoryFilter(parent, style, listener);
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
	protected Panel4AlarmHistoryData createPanel4AlarmHistoryData(Composite parent, int style, int rowCount, Panel4AlarmHistoryDataListenerIf listener) {
		return new Panel4AlarmHistoryData(parent, style, rowCount, listener);
	}

	/**
	 * 현재알람 페이지의 어드바이저를 생성합니다.
	 * 
	 * @return 현재알람 페이지의 어드바이저
	 */
	protected Page4AlarmHistoryAdvisor createPage4AlarmHistoryAdvisor() {
		return new Page4AlarmHistoryAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListAlarmHistory(panel4AlarmHistoryData.getStartNo(), progressBar);
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
			panel4AlarmHistoryFilter.setSeverity(null);
			queryListAlarmHistory(0, true);
		} else if (node.isNe()) {
			panel4AlarmHistoryFilter.setSeverity(null);
			queryListAlarmHistory(0, true);
		}
	}

	/**
	 * @param severity
	 */
	public void display(SEVERITY severity) {
		panel4AlarmHistoryFilter.setSeverity(severity);
		queryListAlarmHistory(0, true);
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
	protected void queryListAlarmHistory(final int startNo, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final EMP_MODEL_EVENT event_def = panel4AlarmHistoryFilter.getEventCode();
			final SEVERITY severity = panel4AlarmHistoryFilter.getSeverity();
			final Date fromDate = panel4AlarmHistoryFilter.getFromDate();
			final Date toDate = panel4AlarmHistoryFilter.getToDate();

			TablePageConfig<Model4Alarm> pageConfig = (TablePageConfig<Model4Alarm>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListAlarmHistory(node, event_def, severity, fromDate, toDate, startNo, ROW_COUNT);
				}
			});

			panel4AlarmHistoryData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), ex);
		}
	}

	protected void saveExcelAlarmHistory(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final EMP_MODEL_EVENT event_def = panel4AlarmHistoryFilter.getEventCode();
			final SEVERITY severity = panel4AlarmHistoryFilter.getSeverity();
			final Date fromDate = panel4AlarmHistoryFilter.getFromDate();
			final Date toDate = panel4AlarmHistoryFilter.getToDate();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelAlarmHistory(node, event_def, severity, fromDate, toDate);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_HISTORY), ex);
		}
	}

}
