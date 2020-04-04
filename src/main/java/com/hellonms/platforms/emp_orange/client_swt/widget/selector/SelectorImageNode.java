package com.hellonms.platforms.emp_orange.client_swt.widget.selector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * SelectorImageNode
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorImageNode extends Composite implements ControlInputIf {

	public interface SelectorImageNodePopupListenerIf {

		public void open(SelectorImageNode selectorImageNode, String[] image_paths, String image_path);

	}

	private static SelectorImageNodePopupListenerIf popupListener = new SelectorImageNodePopupListenerIf() {
		@Override
		public void open(SelectorImageNode selectorImageNode, String[] image_paths, String image_path) {
			SelectorImageNodePopup popup = new SelectorImageNodePopup(selectorImageNode.getShell(), image_paths, image_path);
			popup.setLocation(selectorImageNode.toDisplay(selectorImageNode.getSize().x - popup.getSize().x, selectorImageNode.getSize().y + 1));
			popup.open();
			if (popup.getImagePath() != null) {
				selectorImageNode.setValue(popup.getImagePath());
			}
		}
	};

	private Label label_image;

	private String value = "";

	private String[] image_paths = {};

	protected PanelInputListenerIf listener;

	private ModifyListener modifyListener;

	public SelectorImageNode(Composite parent, int style) {
		super(parent, style);
		createGUI();
	}

	@Override
	protected void checkSubclass() {
		super.checkSubclass();
	}

	protected void createGUI() {
		GridLayout gl = new GridLayout(2, false);
		setLayout(gl);
		setBackground(UtilResource.getColor(255, 255, 255));

		label_image = new Label(this, SWT.NONE);
		GridData gd_label_image = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd_label_image.heightHint = 32;
		gd_label_image.widthHint = 32;
		label_image.setLayoutData(gd_label_image);

		Button button = new Button(this, SWT.ARROW | SWT.DOWN);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				popupListener.open(SelectorImageNode.this, image_paths, SelectorImageNode.this.value);
			}
		});
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		return value != null;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		if (value instanceof String) {
			this.value = (String) value;
			label_image.setImage(UtilResource4Orange.getNetworkMapIcon(this.value, SEVERITY.CLEAR));
			if (listener != null) {
				listener.completeChanged();
			}
			if (modifyListener != null) {
				modifyListener.modifyText(null);
			}
		}
	}

	public void addModifyListener(ModifyListener modifyListener) {
		this.modifyListener = modifyListener;
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.listener = listener;
	}

	public void setImagePaths(String[] image_paths) {
		this.image_paths = image_paths;
	}

}
