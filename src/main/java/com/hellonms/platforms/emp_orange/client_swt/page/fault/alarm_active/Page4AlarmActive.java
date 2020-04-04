package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active;

import java.io.File;

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
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.Panel4AlarmActiveData.Panel4AlarmActiveDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_active.Panel4AlarmActiveFilter.Panel4AlarmActiveFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Alarm;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4AlarmActive
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmActive extends PageNode {

	/**
	 * 현재알람의 검색 판넬과 검색결과 판넬의 리스너 인터페이스를 구현한 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4AlarmActiveChildListener implements //
			Panel4AlarmActiveFilterListenerIf, //
			Panel4AlarmActiveDataListenerIf {

		@Override
		public void queryListAlarmActive(int startNo) {
			Page4AlarmActive.this.queryListAlarmActive(startNo, true);
		}

		@Override
		public void ackAlarm(long gen_event_id) {
			Page4AlarmActive.this.ackAlarm(gen_event_id, true);
		}

		@Override
		public void clearAlarmByUser(long gen_event_id) {
			Page4AlarmActive.this.clearAlarm(gen_event_id, true);
		}

		@Override
		public void saveExcelAlarmActive(String path) {
			Page4AlarmActive.this.saveExcelAlarmActive(path, true);
		}

	}

	/**
	 * 현재알람 검색결과 판넬의 테이블 항목의 최대 개수
	 */
	protected final int ROW_COUNT = 20;

	/**
	 * 현재알람 검색 판넬
	 */
	protected Panel4AlarmActiveFilter panel4AlarmActiveFilter;

	/**
	 * 현재알람 검색결과 판넬
	 */
	protected Panel4AlarmActiveData panel4AlarmActiveData;

	/**
	 * 현재알람 페이지의 어드바이저
	 */
	protected Page4AlarmActiveAdvisor advisor;

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
	public Page4AlarmActive(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, listener);

		advisor = createPage4AlarmActiveAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4AlarmActiveFilter = createPanel4AlarmActiveFilter(contentComposite, SWT.NONE, new Page4AlarmActiveChildListener());
		FormData fd_panel4AlarmActiveFilter = new FormData();
		fd_panel4AlarmActiveFilter.right = new FormAttachment(0, 245);
		fd_panel4AlarmActiveFilter.bottom = new FormAttachment(100, -5);
		fd_panel4AlarmActiveFilter.top = new FormAttachment(0, 5);
		fd_panel4AlarmActiveFilter.left = new FormAttachment(0, 5);
		panel4AlarmActiveFilter.setLayoutData(fd_panel4AlarmActiveFilter);

		panel4AlarmActiveData = createPanel4AlarmActiveData(contentComposite, SWT.NONE, ROW_COUNT, new Page4AlarmActiveChildListener());
		FormData fd_panel4AlarmActiveData = new FormData();
		fd_panel4AlarmActiveData.left = new FormAttachment(panel4AlarmActiveFilter, 6);
		fd_panel4AlarmActiveData.right = new FormAttachment(100, -5);
		fd_panel4AlarmActiveData.bottom = new FormAttachment(panel4AlarmActiveFilter, 0, SWT.BOTTOM);
		fd_panel4AlarmActiveData.top = new FormAttachment(panel4AlarmActiveFilter, 0, SWT.TOP);
		panel4AlarmActiveData.setLayoutData(fd_panel4AlarmActiveData);

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
	protected Panel4AlarmActiveFilter createPanel4AlarmActiveFilter(Composite parent, int style, Panel4AlarmActiveFilterListenerIf listener) {
		return new Panel4AlarmActiveFilter(parent, style, listener);
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
	protected Panel4AlarmActiveData createPanel4AlarmActiveData(Composite parent, int style, int rowCount, Panel4AlarmActiveDataListenerIf listener) {
		return new Panel4AlarmActiveData(parent, style, rowCount, listener);
	}

	/**
	 * 현재알람 페이지의 어드바이저를 생성합니다.
	 * 
	 * @return 현재알람 페이지의 어드바이저
	 */
	protected Page4AlarmActiveAdvisor createPage4AlarmActiveAdvisor() {
		return new Page4AlarmActiveAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListAlarmActive(panel4AlarmActiveData.getStartNo(), progressBar);
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
		panel4AlarmActiveFilter.setSeverity(null);
		queryListAlarmActive(0, true);
	}

	/**
	 * @param severity
	 */
	public void display(NODE node, SEVERITY severity) {
		super.display(node);
		panel4AlarmActiveFilter.setSeverity(severity);
		queryListAlarmActive(0, true);
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
	protected void queryListAlarmActive(final int startNo, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final SEVERITY severity = panel4AlarmActiveFilter.getSeverity();

			TablePageConfig<Model4Alarm> pageConfig = (TablePageConfig<Model4Alarm>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListAlarmActive(node, severity, startNo, ROW_COUNT);
				}
			});

			panel4AlarmActiveData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected void ackAlarm(final long gen_event_id, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final SEVERITY severity = panel4AlarmActiveFilter.getSeverity();
			final int startNo = panel4AlarmActiveData.getStartNo();

			TablePageConfig<Model4Alarm> pageConfig = (TablePageConfig<Model4Alarm>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.ackAlarm(gen_event_id, node, severity, startNo, ROW_COUNT);
				}
			});

			panel4AlarmActiveData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected void clearAlarm(final long gen_event_id, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final SEVERITY severity = panel4AlarmActiveFilter.getSeverity();
			final int startNo = panel4AlarmActiveData.getStartNo();

			TablePageConfig<Model4Alarm> pageConfig = (TablePageConfig<Model4Alarm>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.clearAlarmByGen_first_event_id(gen_event_id, node, severity, startNo, ROW_COUNT);
				}
			});

			panel4AlarmActiveData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		}
	}

	protected void saveExcelAlarmActive(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final SEVERITY severity = panel4AlarmActiveFilter.getSeverity();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelAlarmActive(node, null, severity);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_ACTIVE), ex);
		}
	}

}
