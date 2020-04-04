package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.DataFactory;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorCombo;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.page.DataBuilder4Orange.DATA_COMBO_ORANGE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group.SelectorTree4NeGroup;
import com.hellonms.platforms.emp_orange.client_swt.widget.selector.SelectorImageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * PanelInput4Ne
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelInput4Ne extends PanelInput4NeAt {

	/**
	 * 상위 NE 그룹 셀렉터
	 */
	protected SelectorTree4NeGroup selectorTreeNeGroupParent;

	/**
	 * NE 이름 입력 필드
	 */
	protected TextInput4String textName;

	protected SelectorCombo selectorNeType;

	/**
	 * NE IP 입력 필드
	 */
	protected TextInput4String textHost;

	private SelectorImageNode selectorImageNode;

	/**
	 * 설명 입력 필드
	 */
	protected TextInput4String textDescription;

	/**
	 * NE 모델
	 */
	protected Model4Ne model;

	private String[] image_paths;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            입력 판넬 리스너
	 */
	public PanelInput4Ne(Composite parent, int style, String[] image_paths, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);
		this.image_paths = image_paths;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new GridLayout(2, false));

		LabelText labelParent = new LabelText(getContentComposite(), SWT.NONE, true);
		labelParent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelParent.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_GROUP_NAME));

		selectorTreeNeGroupParent = new SelectorTree4NeGroup(getContentComposite(), SWT.NONE);
		selectorTreeNeGroupParent.setNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
		selectorTreeNeGroupParent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}
		});
		selectorTreeNeGroupParent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelName = new LabelText(getContentComposite(), SWT.NONE, true);
		labelName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_NAME));

		textName = new TextInput4String(getContentComposite(), SWT.BORDER);
		textName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelNeType = new LabelText(getContentComposite(), SWT.NONE, true);
		labelNeType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelNeType.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_TYPE));

		selectorNeType = new SelectorCombo(getContentComposite(), SWT.READ_ONLY);
		selectorNeType.setDataCombo(DataFactory.createDataCombo(DATA_COMBO_ORANGE.NE_TYPE));
		selectorNeType.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				displayIcon();
				checkComplete();
			}
		});
		selectorNeType.getControl().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelHost = new LabelText(getContentComposite(), SWT.NONE, true);
		labelHost.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelHost.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_IP));

		textHost = new TextInput4String(getContentComposite(), SWT.BORDER);
		textHost.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		textHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelIcon = new LabelText(getContentComposite(), SWT.NONE);
		labelIcon.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelIcon.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ICON));

		selectorImageNode = new SelectorImageNode(getContentComposite(), SWT.BORDER);
		selectorImageNode.setImagePaths(image_paths);
		selectorImageNode.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		selectorImageNode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelDescription = new LabelText(getContentComposite(), SWT.NONE);
		labelDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelDescription.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION));

		textDescription = new TextInput4String(getContentComposite(), SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textDescription.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		GridData gd_textDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textDescription.widthHint = 400;
		textDescription.setLayoutData(gd_textDescription);
	}

	@Override
	protected void checkComplete() {
		Model4NeGroup parentNeGroup = selectorTreeNeGroupParent.getNeGroup();
		if (parentNeGroup == null) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.PARENT_NE_GROUP));
			setComplete(false);
		} else if (textName.getText().trim().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE_NAME));
			setComplete(false);
		} else if (textHost.getText().trim().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE_IP));
			setComplete(false);
		} else {
			setErrorMessage(null);
			setComplete(true);
		}

		completeChanged();
	}

	@Override
	public Model4Ne getModel() {
		applyModel();
		return model;
	}

	@Override
	public void setModel(Model4Ne model) {
		this.model = model.copy();
		displayModel();
	}

	@Override
	public void setNe_group_id(int ne_group_id) {
		if (model != null) {
			model.setNe_group_id(ne_group_id);
			displayModel();
		}
	}

	protected void displayIcon() {
		if (model != null && model.getNe_code() == 0 && model.getNe_icon().equals(EMP_MODEL_NE.NE_ICON)) {
			Integer ne_code = (Integer) selectorNeType.getSelectedItem();
			if (ne_code != null) {
				EMP_MODEL_NE ne_def = EMP_MODEL.current().getNe(ne_code);
				if (ne_def != null) {
					selectorImageNode.setValue(ne_def.getNe_icon());
				}
			}
		}
	}

	protected void displayModel() {
		if (model != null) {
			selectorTreeNeGroupParent.setNeGroup(model.getNe_group_id());
			textName.setText(model.getNe_name());
			selectorNeType.setSelectedItem(model.getNe_code());
			textHost.setText(model.getNeSessions().length > 0 ? model.getNeSessions()[0].getHost() : "");
			selectorImageNode.setValue(model.getNe_icon());
			textDescription.setText(model.getDescription());
		}
		displayIcon();
		checkComplete();
	}

	protected void applyModel() {
		if (model != null) {
			Model4NeGroup parentNeGroup = selectorTreeNeGroupParent.getNeGroup();
			String ne_name = textName.getText().trim();
			Integer ne_code = (Integer) selectorNeType.getSelectedItem();
			String description = textDescription.getText();

			model.setNe_group_id(parentNeGroup.getNe_group_id());
			model.setNe_name(ne_name);
			model.setNe_code(ne_code == null ? 0 : ne_code);
			model.setNe_icon(selectorImageNode.getValue());
			model.setDescription(description);
		}
	}

	@Override
	public void setHostEditable(boolean editable) {
		textHost.setEditable(editable);
	}

	@Override
	public String getHost() {
		return textHost.getText().trim();
	}

}
