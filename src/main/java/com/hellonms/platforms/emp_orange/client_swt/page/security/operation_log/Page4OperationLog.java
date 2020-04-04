package com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log;

import java.io.File;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogProgress.ProgressTaskIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.Panel4OperationLogData.Panel4OperationLogDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.security.operation_log.Panel4OperationLogFilter.Panel4OperationLogFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.Model4OperationLog;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * Page4OperationLog
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4OperationLog extends PageNode {

	protected class ChildListener implements //
			Panel4OperationLogFilterListenerIf, //
			Panel4OperationLogDataListenerIf {

		@Override
		public void queryListOperationLog(int startNo) {
			Page4OperationLog.this.queryListOperationLog(startNo, true);
		}

		@Override
		public void saveExcelOperationLog(String path) {
			Page4OperationLog.this.saveExcelOperationLog(path, true);
		}

	}

	/**
	 * 현재알람 검색결과 판넬의 테이블 항목의 최대 개수
	 */
	protected final int ROW_COUNT = 20;

	/**
	 * 현재알람 검색 판넬
	 */
	protected Panel4OperationLogFilter panel4OperationLogFilter;

	protected Panel4OperationLogData panel4OperationLogData;

	protected Page4OperationLogAdvisor advisor;

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
	public Page4OperationLog(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, listener);

		advisor = createPage4OperationLogAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4OperationLogFilter = createPanel4OperationLogFilter(contentComposite, SWT.NONE, new ChildListener());
		FormData fd_panel4OperationLogFilter = new FormData();
		fd_panel4OperationLogFilter.right = new FormAttachment(0, 245);
		fd_panel4OperationLogFilter.bottom = new FormAttachment(100, -5);
		fd_panel4OperationLogFilter.top = new FormAttachment(0, 5);
		fd_panel4OperationLogFilter.left = new FormAttachment(0, 5);
		panel4OperationLogFilter.setLayoutData(fd_panel4OperationLogFilter);

		panel4OperationLogData = createPanel4OperationLogData(contentComposite, SWT.NONE, ROW_COUNT, new ChildListener());
		FormData fd_panel4OperationLogData = new FormData();
		fd_panel4OperationLogData.left = new FormAttachment(panel4OperationLogFilter, 6);
		fd_panel4OperationLogData.right = new FormAttachment(100, -5);
		fd_panel4OperationLogData.bottom = new FormAttachment(panel4OperationLogFilter, 0, SWT.BOTTOM);
		fd_panel4OperationLogData.top = new FormAttachment(panel4OperationLogFilter, 0, SWT.TOP);
		panel4OperationLogData.setLayoutData(fd_panel4OperationLogData);

		return contentComposite;
	}

	protected Panel4OperationLogFilter createPanel4OperationLogFilter(Composite parent, int style, Panel4OperationLogFilterListenerIf listener) {
		OPERATION_CODE[] operation_codes = {};
		try {
			operation_codes = createPage4OperationLogAdvisor().getListOperationCode();
		} catch (EmpException e) {
			e.printStackTrace();
		}
		return new Panel4OperationLogFilter(parent, style, operation_codes, listener);
	}

	protected Panel4OperationLogData createPanel4OperationLogData(Composite parent, int style, int rowCount, Panel4OperationLogDataListenerIf listener) {
		return new Panel4OperationLogData(parent, style, rowCount, listener);
	}

	protected Page4OperationLogAdvisor createPage4OperationLogAdvisor() {
		return new Page4OperationLogAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		queryListOperationLog(panel4OperationLogData.getStartNo(), progressBar);
	}

	@Override
	public void display(NODE node) {
		super.display(node);
		display(true);
	}

	@SuppressWarnings("unchecked")
	protected void queryListOperationLog(final int startNo, boolean progressBar) {
		try {
			final String service = panel4OperationLogFilter.getService();
			final String function = panel4OperationLogFilter.getFunction();
			final String operation = panel4OperationLogFilter.getOperation();
			final Boolean result = panel4OperationLogFilter.getResult();
			final Integer sessionId = panel4OperationLogFilter.getSessionId();
			final String userId = panel4OperationLogFilter.getUserId();
			final Date fromDate = panel4OperationLogFilter.getFromTime();
			final Date toDate = panel4OperationLogFilter.getToTime();

			TablePageConfig<Model4OperationLog> pageConfig = (TablePageConfig<Model4OperationLog>) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListOperationLog(node, service, function, operation, result, sessionId, userId, fromDate, toDate, startNo, ROW_COUNT);
				}
			});

			panel4OperationLogData.display(pageConfig);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG), ex);
		}
	}

	protected void saveExcelOperationLog(String path, boolean progressBar) {
		try {
			final String service = panel4OperationLogFilter.getService();
			final String function = panel4OperationLogFilter.getFunction();
			final String operation = panel4OperationLogFilter.getOperation();
			final Boolean result = panel4OperationLogFilter.getResult();
			final Integer sessionId = panel4OperationLogFilter.getSessionId();
			final String userId = panel4OperationLogFilter.getUserId();
			final Date fromDate = panel4OperationLogFilter.getFromTime();
			final Date toDate = panel4OperationLogFilter.getToTime();

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelOperationLog(node, service, function, operation, result, sessionId, userId, fromDate, toDate);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);

				String[] cmdarray = new String[] { "cmd.exe", "/c", toFile.getAbsolutePath() };
				Runtime.getRuntime().exec(cmdarray);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.OPERATION_LOG), ex);
		}
	}

}
