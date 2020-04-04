package com.hellonms.platforms.emp_orange.client_swt.page.emp_model.snmp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.parser.snmp.MIB_NODE;
import com.hellonms.platforms.emp_util.string.UtilString;

public class Panel4SnmpMoidDetail extends Panel {

	public interface Panel4SnmpMoidDetailListnerIf extends Panel4SnmpListenerIf {
	}

	private TextInput text_oid;
	private TextInput text_mib;

	@SuppressWarnings("unused")
	private Panel4SnmpMoidDetailListnerIf listener;

	public Panel4SnmpMoidDetail(Composite parent, int style, Panel4SnmpMoidDetailListnerIf listener) {
		super(parent, style, EMP_MODEL.EVENT);
		this.listener = listener;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		getContentComposite().setLayout(new FormLayout());

		PanelRound panelData = new PanelRound(getContentComposite(), SWT.NONE);
		FormData fd_panelData = new FormData();
		fd_panelData.top = new FormAttachment(0, 5);
		fd_panelData.bottom = new FormAttachment(100, -5);
		fd_panelData.right = new FormAttachment(100, -5);
		fd_panelData.left = new FormAttachment(0, 5);
		panelData.setLayoutData(fd_panelData);
		{
			panelData.getContentComposite().setLayout(new GridLayout(2, false));

			LabelText label_oid = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_oid.setText("Oid");
			label_oid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_oid = new TextInput(panelData.getContentComposite(), SWT.BORDER | SWT.READ_ONLY);
			text_oid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			LabelText label_mib = new LabelText(panelData.getContentComposite(), SWT.NONE, true);
			label_mib.setText("MIB");
			label_mib.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

			text_mib = new TextInput(panelData.getContentComposite(), SWT.BORDER | SWT.READ_ONLY | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			text_mib.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
	}

	public void displayDetail(MIB_NODE node) {
		if (node == null) {
			setTitle(UtilString.format("{}", "MIB"));
			text_oid.setText("");
			text_mib.setText("");
		} else {
			setTitle(UtilString.format("{} - {}", "MIB", node.getName()));
			text_oid.setText(node.getOidString());
			text_mib.setText(node.toString());
		}
	}

}
