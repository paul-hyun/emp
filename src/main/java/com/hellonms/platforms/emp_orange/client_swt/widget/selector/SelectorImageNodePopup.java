package com.hellonms.platforms.emp_orange.client_swt.widget.selector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;

/**
 * <p>
 * SelectorImageNodePopup
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorImageNodePopup extends PanelPopup {

	private Composite image_composite;

	private String[] image_paths;

	private String image_path;

	private boolean perform_finish;

	public SelectorImageNodePopup(Shell shell, String[] image_paths, String image_path) {
		super(shell);
		this.image_paths = image_paths;
		this.image_path = image_path;

		createGUI();
	}

	@Override
	protected void checkSubclass() {
		super.checkSubclass();
	}

	protected void createGUI() {
		setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		setSize(400, 320);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
				e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
			}
		});

		setLayout(new GridLayout(2, false));

		ScrolledComposite scroll = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		image_composite = new Composite(scroll, SWT.NONE);
		image_composite.setBackground(UtilResource.getColor(255, 255, 255));
		image_composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		scroll.setContent(image_composite);
		GridLayout gl_image_composite = new GridLayout(5, false);
		gl_image_composite.horizontalSpacing = 15;
		gl_image_composite.verticalSpacing = 15;
		image_composite.setLayout(gl_image_composite);

		for (String image_path : image_paths) {
			final Label label_image = new Label(image_composite, SWT.NONE);
			label_image.setImage(UtilResource4Orange.getNetworkDetailIcon(image_path, SEVERITY.CLEAR));
			label_image.setData(image_path);
			GridData gd_label_image = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			gd_label_image.heightHint = 64;
			gd_label_image.widthHint = 64;
			label_image.setLayoutData(gd_label_image);
			label_image.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					if (label_image.getData().equals(SelectorImageNodePopup.this.image_path)) {
						e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
						e.gc.drawRectangle(0, 0, label_image.getSize().x - 1, label_image.getSize().y - 1);
					}
				}
			});
			label_image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					SelectorImageNodePopup.this.selected(label_image);
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					SelectorImageNodePopup.this.selected(label_image);
					SelectorImageNodePopup.this.performFinish();
				}

			});
		}

		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(image_composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		ButtonClick buttonOk = new ButtonClick(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorImageNodePopup.this.performFinish();
			}
		});
		GridData gd_buttonOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_buttonOk.widthHint = 60;
		buttonOk.setLayoutData(gd_buttonOk);
		buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));

		ButtonClick buttonCancel = new ButtonClick(this, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorImageNodePopup.this.performCancel();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));
	}

	protected void selected(Label label_image) {
		this.image_path = (String) label_image.getData();
		for (Control control : image_composite.getChildren()) {
			control.redraw();
		}
	}

	/**
	 * 완료작업을 수행합니다.
	 */
	protected void performFinish() {
		perform_finish = true;
		dispose();
	}

	/**
	 * 취소작업을 수행합니다.
	 */
	protected void performCancel() {
		perform_finish = false;
		dispose();
	}

	public String getImagePath() {
		return perform_finish ? image_path : null;
	}

}
