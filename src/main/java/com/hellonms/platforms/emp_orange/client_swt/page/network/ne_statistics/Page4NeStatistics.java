package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

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
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeStatistics;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.Panel4NeStatisticsDataAt.Panel4NeStatisticsDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics.Panel4NeStatisticsFilter.Panel4NeStatisticsFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4NeStatistics
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NeStatistics extends PageNode {

	/**
	 * 성능통계 페이지의 하위 클래스의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4NeStatisticsChildListener implements //
			Panel4NeStatisticsFilterListenerIf, //
			Panel4NeStatisticsDataListenerIf {

		@Override
		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_code) {
			Page4NeStatistics.this.selectionChanged(ne_info_code);
			Page4NeStatistics.this.queryListNeStatistics(true);
		}

		@Override
		public void queryListNeStatistics() {
			Page4NeStatistics.this.queryListNeStatistics(true);
		}

		@Override
		public void saveExcelNeStatistics(String path) {
			Page4NeStatistics.this.saveExcelNeStatistics(path, true);
		}

	}

	/**
	 * 성능통계 검색 판넬
	 */
	protected Panel4NeStatisticsFilter panel4NeStatisticsFilter;

	/**
	 * 성능통계 검색결과 판넬
	 */
	protected Panel4NeStatisticsDataAt panel4NeStatisticsData;

	/**
	 * 어드바이저
	 */
	protected Page4NeStatisticsAdvisor advisor;

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
	public Page4NeStatistics(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, null);
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4NeStatisticsFilter = createPanel4NeStatisticsFilter(contentComposite, SWT.NONE, new Page4NeStatisticsChildListener());
		FormData fd_panel4NeStatisticsFilter = new FormData();
		fd_panel4NeStatisticsFilter.right = new FormAttachment(0, 245);
		fd_panel4NeStatisticsFilter.bottom = new FormAttachment(100, -5);
		fd_panel4NeStatisticsFilter.top = new FormAttachment(0, 5);
		fd_panel4NeStatisticsFilter.left = new FormAttachment(0, 5);
		panel4NeStatisticsFilter.setLayoutData(fd_panel4NeStatisticsFilter);

		panel4NeStatisticsData = createPanel4NeStatisticsData(contentComposite, SWT.NONE, new Page4NeStatisticsChildListener());
		FormData fd_panel4NeStatisticsData = new FormData();
		fd_panel4NeStatisticsData.left = new FormAttachment(panel4NeStatisticsFilter, 6);
		fd_panel4NeStatisticsData.right = new FormAttachment(100, -5);
		fd_panel4NeStatisticsData.bottom = new FormAttachment(panel4NeStatisticsFilter, 0, SWT.BOTTOM);
		fd_panel4NeStatisticsData.top = new FormAttachment(panel4NeStatisticsFilter, 0, SWT.TOP);
		panel4NeStatisticsData.setLayoutData(fd_panel4NeStatisticsData);

		advisor = createPage4NeStatisticsAdvisor();

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
	protected Panel4NeStatisticsFilter createPanel4NeStatisticsFilter(Composite parent, int style, Panel4NeStatisticsFilterListenerIf listener) {
		return new Panel4NeStatisticsFilter(parent, style, listener);
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
	protected Panel4NeStatisticsDataAt createPanel4NeStatisticsData(Composite parent, int style, Panel4NeStatisticsDataListenerIf listener) {
		return new Panel4NeStatisticsData(parent, style, listener);
	}

	/**
	 * 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4NeStatisticsAdvisor createPage4NeStatisticsAdvisor() {
		return new Page4NeStatisticsAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		selectionChanged();
		queryListNeStatistics(progressBar);
	}

	@Override
	public void display(NODE node) {
		if (node.isNe()) {
			super.display(node);
			display(true);
		} else {
			try {
				this.node = ModelClient4NetworkTree.getInstance().getFirstNe(node);
				if (this.node == null) {
					this.node = ModelClient4NetworkTree.getInstance().getFirstNe();
				}
				super.display(this.node);
				display(true);
			} catch (EmpException e) {
				ModelDisplay4NeStatistics modelDisplay4Statistics = new ModelDisplay4NeStatistics();
				panel4NeStatisticsFilter.display(modelDisplay4Statistics);
				panel4NeStatisticsData.display(modelDisplay4Statistics);
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			}
		}
	}

	protected EMP_MODEL_NE_INFO getNeInfoCode() {
		return panel4NeStatisticsFilter.getNeInfoCode();
	}

	protected void selectionChanged() {
		if (node == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			return;
		}

		try {
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			EMP_MODEL_NE_INFO[] ne_info_codes = advisor.getListInfo_code(model4Ne.getNe_def());
			if (panel4NeStatisticsFilter != null) {
				panel4NeStatisticsFilter.display(ne_info_codes);
			}
			EMP_MODEL_NE_INFO ne_info_code = getNeInfoCode();
			if (ne_info_code == null && 0 < ne_info_codes.length) {
				ne_info_code = ne_info_codes[0];
			}
			selectionChanged(ne_info_code);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		}
	}

	protected void selectionChanged(EMP_MODEL_NE_INFO ne_info_code) {
		try {
			panel4NeStatisticsData.display(ne_info_code);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		}
	}

	/**
	 * 성능통계 리스트를 조회합니다.
	 * 
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void queryListNeStatistics(boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final Date fromDate = panel4NeStatisticsFilter.getFromTime();
			final Date toDate = panel4NeStatisticsFilter.getToTime();
			final EMP_MODEL_NE_INFO ne_info_code = panel4NeStatisticsFilter.getNeInfoCode();
			if (ne_info_code == null) {
				return;
			}
			final NE_INFO_INDEX ne_info_index = panel4NeStatisticsFilter.getNeInfoIndex();
			final STATISTICS_TYPE statistics_type = panel4NeStatisticsFilter.getStatisticsType();
			if (statistics_type == null) {
				return;
			}

			ModelDisplay4NeStatistics modelDisplay4Statistics = (ModelDisplay4NeStatistics) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListNeStatistics(model4Ne, ne_info_code, ne_info_index, statistics_type, fromDate, toDate);
				}
			});

			panel4NeStatisticsFilter.display(modelDisplay4Statistics);
			panel4NeStatisticsData.display(modelDisplay4Statistics);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		}
	}

	protected void saveExcelNeStatistics(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final Date fromDate = panel4NeStatisticsFilter.getFromTime();
			final Date toDate = panel4NeStatisticsFilter.getToTime();
			final EMP_MODEL_NE_INFO ne_info_def = panel4NeStatisticsFilter.getNeInfoCode();
			final NE_INFO_INDEX info_index = panel4NeStatisticsFilter.getNeInfoIndex();
			final STATISTICS_TYPE statisticsType = panel4NeStatisticsFilter.getStatisticsType();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelNeStatistics(model4Ne, ne_info_def, info_index, statisticsType, fromDate, toDate);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_STATISTICS), ex);
		}
	}

}
