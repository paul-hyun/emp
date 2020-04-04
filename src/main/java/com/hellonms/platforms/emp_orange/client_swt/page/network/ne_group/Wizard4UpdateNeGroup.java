package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogMessage;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.DialogWizard;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PANEL_INPUT_TYPE;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.operation_log.OPERATION_CODE_ORANGE;

/**
 * <p>
 * Wizard4UpdateNeGroup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Wizard4UpdateNeGroup extends Wizard {

	/**
	 * NE그룹 등록정보 위자드의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Wizard4UpdateNeGroupListenerIf {

		/**
		 * NE그룹의 등록정보를 수정합니다.
		 * 
		 * @param wizard
		 *            위자드
		 * @param neGroup
		 *            NE그룹 등록정보
		 * @param newImage
		 *            새 이미지
		 */
		public void updateNeGroup(Wizard4UpdateNeGroup wizard, Model4NeGroup model4NeGroup);

	}

	private static DialogWizard instance;

	/**
	 * NE그룹 등록정보 위자드를 오픈합니다.
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param neGroup
	 *            NE그룹 등록정보
	 * @param listener
	 *            리스너
	 */
	public static void open(Shell shell, Model4NeGroup neGroup, String[] image_paths, Wizard4UpdateNeGroupListenerIf listener) {
		if (instance == null || instance.getShell() == null || instance.getShell().isDisposed()) {
			instance = new DialogWizard(shell, new Wizard4UpdateNeGroup(neGroup, image_paths, listener));
			instance.open();
		}
	}

	/**
	 * NE그룹 등록정보 위자드를 닫습니다.
	 */
	public static void close() {
		if (instance != null && instance.getShell() != null && !instance.getShell().isDisposed()) {
			instance.close();
			instance = null;
		}
	}

	private WizardPage4NeGroup wizardPage4NeGroup;

	private Model4NeGroup model4NeGroup;

	private String[] image_paths;

	private Wizard4UpdateNeGroupListenerIf listener;

	private Wizard4UpdateNeGroup(Model4NeGroup model4NeGroup, String[] image_paths, Wizard4UpdateNeGroupListenerIf listener) {
		setWindowTitle(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP));

		this.model4NeGroup = model4NeGroup;
		this.image_paths = image_paths;
		this.listener = listener;
	}

	@Override
	public void addPages() {
		boolean isManagement = false;
		try {
			NODE node = ModelClient4NetworkTree.getInstance().getNeGroup(model4NeGroup.getNe_group_id());
			isManagement = node.isManagement();
		} catch (EmpException e) {
		}
		boolean checkComplete = model4NeGroup.getAccess().isUpdate() && ApplicationProperty.getOperation_authority(OPERATION_CODE_ORANGE.NETWORK_NE_UPDATE) && isManagement;
		wizardPage4NeGroup = new WizardPage4NeGroup(PANEL_INPUT_TYPE.UPDATE, model4NeGroup, image_paths, checkComplete);
		addPage(wizardPage4NeGroup);
	}

	@Override
	public boolean performFinish() {
		final Model4NeGroup model4NeGroup = wizardPage4NeGroup.getNeGroup();

		if (DialogMessage.openConfirm(getShell(), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_TITLE, MESSAGE_CODE_ORANGE.NE_GROUP), UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.UPDATE_CONFIRM, MESSAGE_CODE_ORANGE.NE_GROUP, model4NeGroup.getNe_group_name()))) {
			listener.updateNeGroup(this, model4NeGroup);
		}
		return false;
	}

}
