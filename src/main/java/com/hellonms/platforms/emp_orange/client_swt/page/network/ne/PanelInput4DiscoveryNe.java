package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer16;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4IpAddress;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;

public class PanelInput4DiscoveryNe extends PanelInput4DiscoveryNeAt {

	protected TextInput4IpAddress textInputHost;

	protected TextInput4Integer16 textInputCounter;

	public PanelInput4DiscoveryNe(Composite parent, int style, PanelInputListenerIf listener) {
		super(parent, style, listener);

		createGUI();
	}

	protected void createGUI() {
		getContentComposite().setLayout(new GridLayout(2, false));

		LabelText labelIp = new LabelText(getContentComposite(), SWT.NONE);
		labelIp.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelIp.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SEARCH_IP));

		textInputHost = new TextInput4IpAddress(getContentComposite(), SWT.BORDER);
		textInputHost.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				checkComplete();
			}
		});
		textInputHost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		LabelText labelPlus = new LabelText(getContentComposite(), SWT.NONE);
		labelPlus.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelPlus.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.IP_COUNTER));

		textInputCounter = new TextInput4Integer16(getContentComposite(), SWT.BORDER, 1, 255);
		textInputCounter.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkComplete();
			}
		});
		textInputCounter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		model = new ModelClient4DiscoveryNe();
		checkComplete();
	}

	@Override
	protected void checkComplete() {
		if (!textInputHost.isComplete()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.SEARCH_IP));
			setComplete(false);
		} else if (!textInputCounter.isComplete()) {
			setErrorMessage(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.INSERT_VALUE, MESSAGE_CODE_ORANGE.IP_COUNTER));
			setComplete(false);
		} else {
			setErrorMessage(null);
			setComplete(true);
		}

		completeChanged();
	}

	@Override
	public ModelClient4DiscoveryNe getModel() {
		applyModel();
		return model;
	}

	protected void applyModel() {
		model.setHost(textInputHost.getText());
		model.setCounter(textInputCounter.getValue());
	}

}
