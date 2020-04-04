package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4String;
import com.hellonms.platforms.emp_orange.client_swt.widget.selector.SelectorImageNode;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

/**
 * <p>
 * PanelInput4NeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelInput4NeGroup extends PanelInput4NeGroupAt {

	protected SelectorTree4NeGroup selectorTreeNeGroupParent;

	protected TextInput4String textInputNeGroupName;

	protected SelectorImageNode selectorImageNode;

	protected TextInput4String textInputDescription;

	private String[] image_paths;

	public PanelInput4NeGroup(Composite parent, int style, String[] image_paths, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
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
		labelParent.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PARENT_NE_GROUP));

		selectorTreeNeGroupParent = new SelectorTree4NeGroup(getContentComposite(), SWT.NONE);
		selectorTreeNeGroupParent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkComplete();
			}
		});
		selectorTreeNeGroupParent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelName = new LabelText(getContentComposite(), SWT.NONE, true);
		labelName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_GROUP_NAME));

		textInputNeGroupName = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputNeGroupName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		textInputNeGroupName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

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

		textInputDescription = new TextInput4String(getContentComposite(), SWT.BORDER);
		textInputDescription.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		textInputDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	@Override
	protected void checkComplete() {
		Model4NeGroup parentNeGroup = selectorTreeNeGroupParent.getNeGroup();
		if (parentNeGroup == null) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INVALID_VALUE, MESSAGE_CODE_ORANGE.PARENT_NE_GROUP));
			setComplete(false);
		} else if (textInputNeGroupName.getText().trim().length() == 0) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.NE_GROUP_NAME));
			setComplete(false);
		} else {
			setErrorMessage(null);
			setComplete(true);
		}

		completeChanged();
	}

	@Override
	public Model4NeGroup getModel() {
		applyModel();
		return model;
	}

	protected void applyModel() {
		if (model != null) {
			Model4NeGroup neGroupParent = selectorTreeNeGroupParent.getNeGroup();
			model.setParent_ne_group_id(neGroupParent == null ? Model4NeGroup.ROOT_NE_GROUP_ID : neGroupParent.getNe_group_id());
			model.setNe_group_name(textInputNeGroupName.getText());
			model.setNe_group_icon(selectorImageNode.getValue());
			model.setDescription(textInputDescription.getText());
		}
	}

	@Override
	public void setModel(Model4NeGroup model) {
		this.model = model.copy();
		displayModel();
	}

	protected void displayModel() {
		if (model != null) {
			selectorTreeNeGroupParent.setNeGroup(model.getParent_ne_group_id());
			selectorTreeNeGroupParent.setEnabled(model.getNe_group_id() != Model4NeGroup.ROOT_NE_GROUP_ID);
			textInputNeGroupName.setText(model.getNe_group_name());
			selectorImageNode.setValue(model.getNe_group_icon());
			textInputDescription.setText(model.getDescription());

			checkComplete();
		}
	}

}
