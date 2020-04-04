package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

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
import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.Panel4AlarmStatisticsData.Panel4AlarmStatisticsDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics.Panel4AlarmStatisticsFilter.Panel4AlarmStatisticsFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics.ITEM;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4AlarmStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4AlarmStatistics extends PageNode {

	/**
	 * 성능통계 페이지의 하위 클래스의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4AlarmStatisticsChildListener implements //
			Panel4AlarmStatisticsFilterListenerIf, //
			Panel4AlarmStatisticsDataListenerIf {

		@Override
		public void queryListAlarmStatistics() {
			Page4AlarmStatistics.this.queryListAlarmStatistics(true);
		}

		@Override
		public void saveExcelAlarmStatistics(String path) {
			Page4AlarmStatistics.this.saveExcelAlarmStatistics(path, true);
		}

	}

	/**
	 * 성능통계 검색 판넬
	 */
	protected Panel4AlarmStatisticsFilter panel4AlarmStatisticsFilter;

	/**
	 * 성능통계 검색결과 판넬
	 */
	protected Panel4AlarmStatisticsData panel4AlarmStatisticsData;

	/**
	 * 어드바이저
	 */
	protected Page4AlarmStatisticsAdvisor advisor;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 필터
	 * @param ne_id_filters
	 *            NE 아이디 필터
	 * @param ne_define_id_filters
	 *            NE 정의 아이디 필터
	 * @param view
	 *            뷰
	 */
	public Page4AlarmStatistics(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, listener);
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4AlarmStatisticsFilter = createPanel4AlarmStatisticsFilter(contentComposite, SWT.NONE, new Page4AlarmStatisticsChildListener());
		FormData fd_panel4AlarmStatisticsFilter = new FormData();
		fd_panel4AlarmStatisticsFilter.right = new FormAttachment(0, 245);
		fd_panel4AlarmStatisticsFilter.bottom = new FormAttachment(100, -5);
		fd_panel4AlarmStatisticsFilter.top = new FormAttachment(0, 5);
		fd_panel4AlarmStatisticsFilter.left = new FormAttachment(0, 5);
		panel4AlarmStatisticsFilter.setLayoutData(fd_panel4AlarmStatisticsFilter);

		panel4AlarmStatisticsData = createPanel4AlarmStatisticsData(contentComposite, SWT.NONE, new Page4AlarmStatisticsChildListener());
		FormData fd_panel4AlarmStatisticsData = new FormData();
		fd_panel4AlarmStatisticsData.left = new FormAttachment(panel4AlarmStatisticsFilter, 6);
		fd_panel4AlarmStatisticsData.right = new FormAttachment(100, -5);
		fd_panel4AlarmStatisticsData.bottom = new FormAttachment(panel4AlarmStatisticsFilter, 0, SWT.BOTTOM);
		fd_panel4AlarmStatisticsData.top = new FormAttachment(panel4AlarmStatisticsFilter, 0, SWT.TOP);
		panel4AlarmStatisticsData.setLayoutData(fd_panel4AlarmStatisticsData);

		advisor = createPage4AlarmStatisticsAdvisor();

		return contentComposite;
	}

	/**
	 * 성능통계 검색 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param ne_group_id_filters
	 *            NE그룹 아이디 필터
	 * @param ne_id_filters
	 *            NE 필터
	 * @param ne_define_id_filters
	 *            NE 정의 아이디 필터
	 * @param listener
	 *            리스너
	 * @return
	 */
	protected Panel4AlarmStatisticsFilter createPanel4AlarmStatisticsFilter(Composite parent, int style, Panel4AlarmStatisticsFilterListenerIf listener) {
		return new Panel4AlarmStatisticsFilter(parent, style, listener);
	}

	/**
	 * 성능통계 검색결과 판넬을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 * @return 성능통계 검색결과 판넬
	 */
	protected Panel4AlarmStatisticsData createPanel4AlarmStatisticsData(Composite parent, int style, Panel4AlarmStatisticsDataListenerIf listener) {
		return new Panel4AlarmStatisticsData(parent, style, listener);
	}

	/**
	 * 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4AlarmStatisticsAdvisor createPage4AlarmStatisticsAdvisor() {
		return new Page4AlarmStatisticsAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListAlarmStatistics(progressBar);
	}

	@Override
	public void display(NODE node) {
		super.display(node);
		display(true);
	}

	protected void initialize() {
		panel4AlarmStatisticsData.display(ITEM.SEVERITY, STATISTICS_TYPE.MINUTE_5, new Model4AlarmStatistics[0]);
	}

	/**
	 * 성능통계 리스트를 조회합니다.
	 * 
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void queryListAlarmStatistics(boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final Date fromDate = panel4AlarmStatisticsFilter.getFromTime();
			final Date toDate = panel4AlarmStatisticsFilter.getToTime();
			final ITEM item = panel4AlarmStatisticsFilter.getItem();
			final STATISTICS_TYPE statisticsType = panel4AlarmStatisticsFilter.getStatisticsType();

			Model4AlarmStatistics[] alarmStatisticss = (Model4AlarmStatistics[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListAlarmStatistics(fromDate, toDate, node, item, statisticsType);
				}
			});

			panel4AlarmStatisticsData.display(item, statisticsType, alarmStatisticss);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), ex);
		}
	}

	protected void saveExcelAlarmStatistics(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final Date fromDate = panel4AlarmStatisticsFilter.getFromTime();
			final Date toDate = panel4AlarmStatisticsFilter.getToTime();
			final ITEM item = panel4AlarmStatisticsFilter.getItem();
			final STATISTICS_TYPE statisticsType = panel4AlarmStatisticsFilter.getStatisticsType();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelAlarmStatistics(node, item, statisticsType, fromDate, toDate);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM_STATISTICS), ex);
		}
	}

}
