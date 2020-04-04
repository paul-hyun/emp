package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

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
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Panel4NeInfoDataAt.Panel4NeInfoDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Panel4NeInfoFilter.Panel4NeInfoFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Wizard4CreateNeInfo.Wizard4CreateNeInfoListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Wizard4DeleteNeInfo.Wizard4DeleteNeInfoListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info.Wizard4UpdateNeInfo.Wizard4UpdateNeInfoListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.error.ERROR_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.file.UtilFile;

/**
 * <p>
 * DataCombo4AlarmSeverity
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4NeInfo extends PageNode {

	/**
	 * 성능이력 페이지의 하위 클래스의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4NeInfoChildListener implements Panel4NeInfoFilterListenerIf, //
			Panel4NeInfoDataListenerIf, //
			Wizard4CreateNeInfoListenerIf, //
			Wizard4UpdateNeInfoListenerIf, //
			Wizard4DeleteNeInfoListenerIf {

		@Override
		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_def) {
			Page4NeInfo.this.selectionChanged(ne_info_def);
			Page4NeInfo.this.queryListNeInfo(0, false, true);
		}

		@Override
		public void queryListNeInfo(int startNo, boolean isQuery) {
			Page4NeInfo.this.queryListNeInfo(startNo, false, true);
		}

		@Override
		public void openWizard4CreateNeInfo() {
			Page4NeInfo.this.openWizard4CreateNeInfo();
		}

		@Override
		public void createNeInfo(Wizard4CreateNeInfo wizard, Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.createNeInfo(wizard, model4NeInfo, true);
		}

		@Override
		public void openWizard4UpdateNeInfo(Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.openWizard4UpdateNeInfo(model4NeInfo);
		}

		@Override
		public void updateNeInfo(Wizard4UpdateNeInfo wizard, Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.updateNeInfo(wizard, model4NeInfo, true);
		}

		@Override
		public void updateNeInfo(Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.updateNeInfo(model4NeInfo, true);
		}

		@Override
		public void openWizard4DeleteNeInfo(Model4NeInfoIf model4NeInfo) {
			Wizard4DeleteNeInfo.open(getShell(), model4NeInfo, new Page4NeInfoChildListener());
		}

		@Override
		public void deleteNeInfo(Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.deleteNeInfo(model4NeInfo, true);
		}

		@Override
		public void deleteNeInfo(Wizard4DeleteNeInfo wizard, Model4NeInfoIf model4NeInfo) {
			Page4NeInfo.this.deleteNeInfo(wizard, model4NeInfo, true);
		}

		@Override
		public void saveExcelNeInfo(String path) {
			Page4NeInfo.this.saveExcelNeInfo(path, true);
		}

	}

	/**
	 * 성능이력 검색 판넬
	 */
	protected Panel4NeInfoFilter panel4NeInfoFilter;

	/**
	 * 성능이력 검색 판넬
	 */
	protected Panel4NeInfoDataAt panel4NeInfoData;

	/**
	 * 어드바이저
	 */
	protected Page4NeInfoAdvisor advisor;

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
	public Page4NeInfo(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, null);

		advisor = createPage4NeInfoAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4NeInfoFilter = createPanel4NeInfoFilter(contentComposite, SWT.NONE, new Page4NeInfoChildListener());
		FormData fd_panel4NeInfoFilter = new FormData();
		fd_panel4NeInfoFilter.right = new FormAttachment(0, 245);
		fd_panel4NeInfoFilter.bottom = new FormAttachment(100, -5);
		fd_panel4NeInfoFilter.top = new FormAttachment(0, 5);
		fd_panel4NeInfoFilter.left = new FormAttachment(0, 5);
		panel4NeInfoFilter.setLayoutData(fd_panel4NeInfoFilter);

		panel4NeInfoData = createPanel4NeInfoData(contentComposite, SWT.NONE, new Page4NeInfoChildListener());
		FormData fd_panel4NeInfoData = new FormData();
		fd_panel4NeInfoData.left = new FormAttachment(panel4NeInfoFilter, 6);
		fd_panel4NeInfoData.right = new FormAttachment(100, -5);
		fd_panel4NeInfoData.bottom = new FormAttachment(panel4NeInfoFilter, 0, SWT.BOTTOM);
		fd_panel4NeInfoData.top = new FormAttachment(panel4NeInfoFilter, 0, SWT.TOP);
		panel4NeInfoData.setLayoutData(fd_panel4NeInfoData);

		return contentComposite;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param parent
	 * @param style
	 * @param listener
	 * @return
	 */
	protected Panel4NeInfoFilter createPanel4NeInfoFilter(Composite parent, int style, Panel4NeInfoFilterListenerIf listener) {
		return new Panel4NeInfoFilter(parent, style, listener);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param parent
	 * @param style
	 * @param listener
	 * @return
	 */
	protected Panel4NeInfoDataAt createPanel4NeInfoData(Composite parent, int style, Panel4NeInfoDataListenerIf listener) {
		return new Panel4NeInfoData(parent, style, listener);
	}

	/**
	 * 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4NeInfoAdvisor createPage4NeInfoAdvisor() {
		return new Page4NeInfoAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		selectionChanged();
		queryListNeInfo(0, false, progressBar);
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
				ModelDisplay4NeInfo modelDisplay4NeInfo = new ModelDisplay4NeInfo();
				displayPanel4NeInfoData(modelDisplay4NeInfo);
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			}
		}
	}

	protected void displayPanel4NeInfoData(ModelDisplay4NeInfo modelDisplay4NeInfo) {
		panel4NeInfoData.display(modelDisplay4NeInfo);
	}

	protected void clearPanel4NeInfoData() {
		panel4NeInfoData.clear();
	}

	protected EMP_MODEL_NE_INFO getNeInfoCode() {
		return panel4NeInfoFilter.getNeInfoCode();
	}

	protected void selectionChanged() {
		if (node == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			return;
		}

		try {
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			EMP_MODEL_NE_INFO[] ne_info_defs = advisor.getListNe_info_def(model4Ne.getNe_def());
			if (panel4NeInfoFilter != null) {
				panel4NeInfoFilter.display(ne_info_defs);
			}
			EMP_MODEL_NE_INFO ne_info_def = getNeInfoCode();
			if (ne_info_def == null && 0 < ne_info_defs.length) {
				ne_info_def = ne_info_defs[0];
			}
			selectionChanged(ne_info_def);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	protected void selectionChanged(EMP_MODEL_NE_INFO ne_info_def) {
		try {
			panel4NeInfoData.display(ne_info_def);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void queryListNeInfo(final int startNo, final boolean isQuery, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final EMP_MODEL_NE_INFO ne_info_def = getNeInfoCode();
			if (ne_info_def == null) {
				return;
			}

			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryListNeInfo(model4Ne, ne_info_def, isQuery);
				}
			});
			
			if (isQuery && modelDisplay4NeInfo.getModel4NeInfo(0) == null) {
				DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(ERROR_CODE_ORANGE.MODEL_NOTEXISTS, MESSAGE_CODE_ORANGE.NE_INFO, ne_info_def == null ? "" : ne_info_def));
			}

			displayPanel4NeInfoData(modelDisplay4NeInfo);
		} catch (EmpException ex) {
			clearPanel4NeInfoData();
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			clearPanel4NeInfoData();
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	protected void openWizard4CreateNeInfo() {
		final EMP_MODEL_NE_INFO ne_info_def = getNeInfoCode();

		try {
			Model4NeInfoIf model4NeInfo = (Model4NeInfoIf) DialogProgress.run(getShell(), true, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.newInstanceNeInfo(ne_info_def);
				}
			});
			Wizard4CreateNeInfo.open(getShell(), model4NeInfo, new Page4NeInfoChildListener());
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}

	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void createNeInfo(Wizard4CreateNeInfo wizard, final Model4NeInfoIf model4NeInfo, boolean progressBar) {
		try {
			model4NeInfo.setNe_id(node.getId());
			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.createNeInfo(model4NeInfo);
				}
			});
			displayPanel4NeInfoData(modelDisplay4NeInfo);
			Wizard4CreateNeInfo.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	protected void openWizard4UpdateNeInfo(Model4NeInfoIf model4NeInfo) {
		Wizard4UpdateNeInfo.open(getShell(), model4NeInfo, new Page4NeInfoChildListener());
	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void updateNeInfo(Wizard4UpdateNeInfo wizard, final Model4NeInfoIf model4NeInfo, boolean progressBar) {
		try {
			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateNeInfo(model4NeInfo);
				}
			});
			displayPanel4NeInfoData(modelDisplay4NeInfo);
			Wizard4UpdateNeInfo.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void updateNeInfo(final Model4NeInfoIf model4NeInfo, boolean progressBar) {
		try {
			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateNeInfo(model4NeInfo);
				}
			});
			displayPanel4NeInfoData(modelDisplay4NeInfo);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void deleteNeInfo(final Model4NeInfoIf model4NeInfo, boolean progressBar) {
		try {
			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.deleteNeInfo(model4NeInfo);
				}
			});
			displayPanel4NeInfoData(modelDisplay4NeInfo);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	/**
	 * 성능이력 리스트를 조회합니다.
	 * 
	 * @param startNo
	 *            시작번호
	 * @param progressBar
	 *            프로그래스바 실행여부
	 */
	protected void deleteNeInfo(Wizard4DeleteNeInfo wizard, final Model4NeInfoIf model4NeInfo, boolean progressBar) {
		try {
			ModelDisplay4NeInfo modelDisplay4NeInfo = (ModelDisplay4NeInfo) DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.deleteNeInfo(model4NeInfo);
				}
			});
			displayPanel4NeInfoData(modelDisplay4NeInfo);
			Wizard4DeleteNeInfo.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

	protected void saveExcelNeInfo(String path, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}

			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final EMP_MODEL_NE_INFO ne_info_def = getNeInfoCode();
			if (ne_info_def == null) {
				return;
			}

			byte[] excel = (byte[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.saveExcelNeInfo(model4Ne, ne_info_def);
				}
			});
			if (excel != null) {
				File toFile = new File(path);
				UtilFile.saveFile(excel, toFile);
			}
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		} catch (Throwable ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), ex);
		}
	}

}
