package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

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
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.Panel4NeThresholdDataAt.Panel4NeThresholdDataListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.Panel4NeThresholdFilter.Panel4NeThresholdFilterListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.Wizard4CopyNeThreshold.Wizard4CopyNeThresholdListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold.Wizard4UpdateNeThreshold.Wizard4UpdateNeThresholdListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Page4NeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public class Page4NeThreshold extends PageNode {

	/**
	 * 성능이력 페이지의 하위 클래스의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class Page4NeThresholdChildListener implements Panel4NeThresholdFilterListenerIf, //
			Wizard4UpdateNeThresholdListenerIf, //
			Panel4NeThresholdDataListenerIf, //
			Wizard4CopyNeThresholdListenerIf {

		@Override
		public void selectionChanged(EMP_MODEL_NE_INFO ne_info_def) {
			Page4NeThreshold.this.selectionChanged(ne_info_def);
			Page4NeThreshold.this.queryNeThreshold(true);
		}

		@Override
		public void queryNeThreshold() {
			Page4NeThreshold.this.queryNeThreshold(true);
		}

		@Override
		public void openWizard4UpdateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
			Page4NeThreshold.this.openWizard4UpdateNeThreshold(ne_threshold, ne_info_field_def);
		}

		@Override
		public void updateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
			Page4NeThreshold.this.updateNeThreshold(ne_threshold, ne_info_field_def, true);
		}

		@Override
		public void updateNeThreshold(Wizard4UpdateNeThreshold wizard, Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
			Page4NeThreshold.this.updateNeThreshold(wizard, ne_threshold, ne_info_field_def, true);
		}

		@Override
		public void openWizard4CopyNeThreshold() {
			Page4NeThreshold.this.openWizard4CopyNeThreshold();
		}

		@Override
		public void copyListNeThreshold(Wizard4CopyNeThreshold wizard, int[] ne_id_targets) {
			Page4NeThreshold.this.copyListNeThreshold(wizard, ne_id_targets, true);
		}

	}

	/**
	 * 성능이력 검색 판넬
	 */
	protected Panel4NeThresholdFilter panel4NeThresholdFilter;

	/**
	 * 성능이력 검색 판넬
	 */
	protected Panel4NeThresholdDataAt panel4NeThresholdData;

	/**
	 * 어드바이저
	 */
	protected Page4NeThresholdAdvisor advisor;

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
	public Page4NeThreshold(Composite parent, int style, PageNodeListenerIf listener) {
		super(parent, style, null);

		advisor = createPage4NeThresholdAdvisor();
	}

	@Override
	protected Composite createContent(Composite parent) {
		Composite contentComposite = new Composite(parent, SWT.NONE);
		contentComposite.setLayout(new FormLayout());
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		panel4NeThresholdFilter = createPanel4NeThresholdFilter(contentComposite, SWT.NONE, new Page4NeThresholdChildListener());
		FormData fd_panel4NeThresholdFilter = new FormData();
		fd_panel4NeThresholdFilter.right = new FormAttachment(0, 245);
		fd_panel4NeThresholdFilter.bottom = new FormAttachment(100, -5);
		fd_panel4NeThresholdFilter.top = new FormAttachment(0, 5);
		fd_panel4NeThresholdFilter.left = new FormAttachment(0, 5);
		panel4NeThresholdFilter.setLayoutData(fd_panel4NeThresholdFilter);

		panel4NeThresholdData = createPanel4NeThresholdData(contentComposite, SWT.NONE, new Page4NeThresholdChildListener());
		FormData fd_panel4NeThresholdData = new FormData();
		fd_panel4NeThresholdData.left = new FormAttachment(panel4NeThresholdFilter, 6);
		fd_panel4NeThresholdData.right = new FormAttachment(100, -5);
		fd_panel4NeThresholdData.bottom = new FormAttachment(panel4NeThresholdFilter, 0, SWT.BOTTOM);
		fd_panel4NeThresholdData.top = new FormAttachment(panel4NeThresholdFilter, 0, SWT.TOP);
		panel4NeThresholdData.setLayoutData(fd_panel4NeThresholdData);

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
	protected Panel4NeThresholdFilter createPanel4NeThresholdFilter(Composite parent, int style, Panel4NeThresholdFilterListenerIf listener) {
		return new Panel4NeThresholdFilter(parent, style, listener);
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
	protected Panel4NeThresholdDataAt createPanel4NeThresholdData(Composite parent, int style, Panel4NeThresholdDataListenerIf listener) {
		return new Panel4NeThresholdData(parent, style, listener);
	}

	/**
	 * 어드바이저를 생성합니다.
	 * 
	 * @return 어드바이저
	 */
	protected Page4NeThresholdAdvisor createPage4NeThresholdAdvisor() {
		return new Page4NeThresholdAdvisor();
	}

	@Override
	public void display(boolean progressBar) {
		selectionChanged();
		queryNeThreshold(progressBar);
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
				clearPanel4NeThresholdData();
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			}
		}
	}

	protected void displayPanel4NeThresholdData(Model4NeThresholdIf ne_threshold) {
		panel4NeThresholdData.display(ne_threshold);
	}

	protected void clearPanel4NeThresholdData() {
		panel4NeThresholdData.clear();
	}

	protected EMP_MODEL_NE_INFO getNeThresholdCode() {
		return panel4NeThresholdFilter.getNeThresholdCode();
	}

	protected void selectionChanged() {
		if (node == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			return;
		}

		try {
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			EMP_MODEL_NE_INFO[] ne_info_defs = advisor.getListNe_threshold_code(model4Ne.getNe_def());
			if (panel4NeThresholdFilter != null) {
				panel4NeThresholdFilter.display(ne_info_defs);
			}
			EMP_MODEL_NE_INFO ne_info_def = getNeThresholdCode();
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
			panel4NeThresholdData.display(ne_info_def);
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
	protected void queryNeThreshold(boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final EMP_MODEL_NE_INFO ne_info_def = getNeThresholdCode();
			if (ne_info_def == null) {
				return;
			}

			Model4NeThresholdIf ne_threshold = (Model4NeThresholdIf) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.queryNeThreshold(model4Ne, ne_info_def);
				}
			});

			displayPanel4NeThresholdData(ne_threshold);
		} catch (EmpException ex) {
			clearPanel4NeThresholdData();
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		} catch (Exception ex) {
			clearPanel4NeThresholdData();
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		}
	}

	protected void openWizard4UpdateNeThreshold(Model4NeThresholdIf ne_threshold, EMP_MODEL_NE_INFO_FIELD ne_info_field_def) {
		Wizard4UpdateNeThreshold.open(getShell(), ne_threshold, ne_info_field_def, new Page4NeThresholdChildListener());
	}

	protected void updateNeThreshold(Wizard4UpdateNeThreshold wizard, final Model4NeThresholdIf ne_threshold, final EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean progressBar) {
		try {
			Model4NeThresholdIf ne_threshold_updated = (Model4NeThresholdIf) DialogProgress.run(wizard.getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateNeThreshold(ne_threshold, ne_info_field_def);
				}
			});
			displayPanel4NeThresholdData(ne_threshold_updated);
			Wizard4UpdateNeThreshold.close();
		} catch (EmpException ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		} catch (Exception ex) {
			DialogMessage.openError(wizard.getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		}
	}

	protected void updateNeThreshold(final Model4NeThresholdIf ne_threshold, final EMP_MODEL_NE_INFO_FIELD ne_info_field_def, boolean progressBar) {
		try {
			Model4NeThresholdIf ne_threshold_updated = (Model4NeThresholdIf) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.updateNeThreshold(ne_threshold, ne_info_field_def);
				}
			});
			displayPanel4NeThresholdData(ne_threshold_updated);
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		}
	}

	protected void openWizard4CopyNeThreshold() {
		if (node == null) {
			DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
			return;
		}
		Model4Ne model4Ne = (Model4Ne) node.getValue();
		Wizard4CopyNeThreshold.open(getShell(), model4Ne, new Page4NeThresholdChildListener());
	}

	public void copyListNeThreshold(Wizard4CopyNeThreshold wizard, final int[] ne_id_targets, boolean progressBar) {
		try {
			if (node == null) {
				DialogMessage.openInfo(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_INFO), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE));
				return;
			}
			final Model4Ne model4Ne = (Model4Ne) node.getValue();
			final EMP_MODEL_NE_INFO ne_info_def = getNeThresholdCode();
			if (ne_info_def == null) {
				return;
			}

			@SuppressWarnings("unused")
			Model4NeThresholdIf[] ne_threshold_updated = (Model4NeThresholdIf[]) DialogProgress.run(getShell(), progressBar, new ProgressTaskIf() {
				@Override
				public Object run() throws EmpException {
					return advisor.copyListNeThreshold(model4Ne, ne_info_def, ne_id_targets);
				}
			});
			Wizard4CopyNeThreshold.close();
		} catch (EmpException ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		} catch (Exception ex) {
			DialogMessage.openError(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_THRESHOLD), ex);
		}
	}

}
