package com.hellonms.platforms.emp_orange.client_swt.page.help.about;

import java.awt.Desktop;
import java.net.URI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.client_swt.widget.dialog.Dialog;

public class Dialog4About extends Dialog {

	protected Image aboutImage;

	protected String aboutText;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param aboutImage
	 *            제품정보 이미지
	 * @param aboutText
	 *            제품정보 내용
	 */
	public Dialog4About(Shell parent, Image aboutImage, String aboutText) {
		super(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		this.aboutImage = aboutImage;
		this.aboutText = aboutText;

		createGUI();
	}

	protected void createGUI() {
		setLayout(new FormLayout());

		Button buttonOk = new Button(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dialog4About.this.dispose();
			}
		});
		FormData fd_buttonOk = new FormData();
		fd_buttonOk.left = new FormAttachment(100, -85);
		fd_buttonOk.bottom = new FormAttachment(100, -5);
		fd_buttonOk.right = new FormAttachment(100, -5);
		buttonOk.setLayoutData(fd_buttonOk);
		buttonOk.setText("Ok");

		Composite panelAbout = new Composite(this, SWT.BORDER);
		panelAbout.setBackground(UtilResource.getColor(SWT.COLOR_WHITE));
		panelAbout.setBackgroundMode(SWT.INHERIT_DEFAULT);
		panelAbout.setLayout(new FormLayout());
		FormData fd_panelAbout = new FormData();
		fd_panelAbout.bottom = new FormAttachment(buttonOk, -5);
		fd_panelAbout.right = new FormAttachment(100);
		fd_panelAbout.top = new FormAttachment(0);
		fd_panelAbout.left = new FormAttachment(0);
		panelAbout.setLayoutData(fd_panelAbout);

		Label imageAboutLabel = new Label(panelAbout, SWT.NONE);
		FormData fd_imageAboutLabel = new FormData();
		fd_imageAboutLabel.top = new FormAttachment(0, 0);
		fd_imageAboutLabel.left = new FormAttachment(0, 0);
		imageAboutLabel.setLayoutData(fd_imageAboutLabel);
		imageAboutLabel.setImage(aboutImage);

		StyledText linkAboutText = new StyledText(panelAbout, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		FormData fd_linkAboutText = new FormData();
		fd_linkAboutText.left = new FormAttachment(imageAboutLabel, 15);
		fd_linkAboutText.bottom = new FormAttachment(100, -5);
		fd_linkAboutText.right = new FormAttachment(100, -5);
		fd_linkAboutText.top = new FormAttachment(imageAboutLabel, 5, SWT.TOP);
		linkAboutText.setLayoutData(fd_linkAboutText);
		linkAboutText.setText(aboutText);

		String[] words = aboutText.split("\\s");
		int start = 0;
		for (String word : words) {
			if (word.contains("://")) {
				StyleRange style = new StyleRange();
				style.underline = true;
				style.underlineStyle = SWT.UNDERLINE_LINK;
				style.start = start;
				style.length = word.length();

				linkAboutText.setStyleRange(style);
			}
			start += (word.length() + 1);
		}

		linkAboutText.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					int offset = linkAboutText.getOffsetAtLocation(new Point(event.x, event.y));
					for (StyleRange style : linkAboutText.getStyleRanges()) {
						if (style.underline && style.underlineStyle == SWT.UNDERLINE_LINK && style.start <= offset && offset <= (style.start + style.length)) {
							String link = linkAboutText.getText(style.start, style.start + style.length - 1);
							Desktop.getDesktop().browse(new URI(link));
							break;
						}
					}
				} catch (Exception e) {
				}
			}
		});
	}

}
