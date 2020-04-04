package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTree4Check;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelBuilder4Orange.TREE_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.PanelFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne.DataTree4Ne;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

/**
 * <p>
 * WizardPage4CopyNeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class WizardPage4CopyNeThreshold extends WizardPage {

	/**
	 * 입력판넬의 리스너 인터페이스를 구현한 리스너 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class ChildListener implements //
			PanelInputListenerIf {

		@Override
		public void completeChanged() {
			WizardPage4CopyNeThreshold.this.checkComplete();
		}

	}

	private Model4Ne model4Ne;

	protected PANEL_INPUT_TYPE panelInputType;

	private DataTree4Ne dataTree;

	private PanelTree4Check panelTree;

	/**
	 * 생성자 입니다.
	 * 
	 * @param model4UserGroups
	 *            사용자그룹 모델 배열
	 */
	public WizardPage4CopyNeThreshold(PANEL_INPUT_TYPE panelInputType, Model4Ne model4Ne) {
		super(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));
		setTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));
		setDescription(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.COPY_TITLE, MESSAGE_CODE_ORANGE.NE_THRESHOLD));

		this.panelInputType = panelInputType;
		this.model4Ne = model4Ne;
	}

	@Override
	public void createControl(Composite parent) {
		dataTree = (DataTree4Ne) DataFactory.createDataTree(DATA_TREE_ORANGE.NE);
		dataTree.setNe_id_filters(new int[] { 1, model4Ne.getNe_id() });

		Composite container = new Composite(parent, SWT.NULL);
		container.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		container.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setControl(container);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.marginWidth = 0;
		gl_container.marginHeight = 0;
		container.setLayout(gl_container);

		panelTree = (PanelTree4Check) PanelFactory.createPanelTree(TREE_ORANGE.NE_COPY, container, SWT.BORDER);
		panelTree.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		panelTree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				WizardPage4CopyNeThreshold.this.checkComplete();
			}
		});
		panelTree.setDataTree(dataTree);
		panelTree.expandToLevel(2);

		checkComplete();
	}

	/**
	 * 완료상태를 확인합니다.
	 */
	protected void checkComplete() {
		int[] ne_ids = getModel();
		if (ne_ids.length == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_PROPERTY, MESSAGE_CODE_ORANGE.COPY));
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}
	}

	/**
	 * 선택된 NE ID를 반환 합니다.
	 * 
	 * @return 선택된 NE ID
	 */
	public int[] getModel() {
		Object[] checkeds = panelTree.getCheckedTopElements();
		List<Integer> checked_ne_list = new ArrayList<Integer>();
		for (Object checked : checkeds) {
			getModel((NODE) checked, checked_ne_list);
		}
		return ArrayUtils.toPrimitive(checked_ne_list.toArray(new Integer[0]));
	}

	private void getModel(NODE node, List<Integer> checked_ne_list) {
		if (node.isNeGroup()) {
			for (Object child : dataTree.getChildren(node)) {
				getModel((NODE) child, checked_ne_list);
			}
		} else if (node.isNe()) {
			checked_ne_list.add(node.getId());
		}
	}

}
