/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.client_swt.widget.action.ActionIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.FONT_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * Page
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 18.
 * @modified 2015. 5. 18.
 * @author cchyun
 *
 */
public class Page extends Composite implements PageIf {

	private Composite contentComposite;

	private String title;

	private String description;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param title
	 *            제목
	 * @param description
	 *            설명
	 */
	public Page(Composite parent, int style, String title, String description) {
		super(parent, style);
		this.title = title;
		this.description = description;

		createGUI();
	}

	private void createGUI() {
		setLayout(new FormLayout());
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));

		if (title != null && !title.equals("")) {
			Label labelTitle = new Label(this, SWT.NONE);
			labelTitle.setForeground(ThemeFactory.getColor(COLOR_ONION.PAGE_TITLE_FG));
			labelTitle.setFont(ThemeFactory.getFont(FONT_ONION.PAGE_TITLE));
			FormData fd_labelTitle = new FormData();
			fd_labelTitle.top = new FormAttachment(0, 5);
			fd_labelTitle.left = new FormAttachment(0, 5);
			fd_labelTitle.right = new FormAttachment(100, -5);
			labelTitle.setLayoutData(fd_labelTitle);
			labelTitle.setText(title);

			Label labelDescription = new Label(this, SWT.NONE);
			labelDescription.setForeground(ThemeFactory.getColor(COLOR_ONION.PAGE_DESCRIPTION_FG));
			labelDescription.setFont(ThemeFactory.getFont(FONT_ONION.PAGE_DESCRIPTION));
			FormData fd_labelDescription = new FormData();
			fd_labelDescription.top = new FormAttachment(labelTitle, 5, SWT.BOTTOM);
			fd_labelDescription.left = new FormAttachment(labelTitle, 0, SWT.LEFT);
			fd_labelDescription.right = new FormAttachment(labelTitle, 0, SWT.RIGHT);
			labelDescription.setLayoutData(fd_labelDescription);
			labelDescription.setText(description);

			contentComposite = new Composite(this, SWT.NONE);
			FormData fd_contentComposite = new FormData();
			fd_contentComposite.top = new FormAttachment(labelDescription, 10, SWT.BOTTOM);
			fd_contentComposite.left = new FormAttachment(labelDescription, 0, SWT.LEFT);
			fd_contentComposite.right = new FormAttachment(labelDescription, 0, SWT.RIGHT);
			fd_contentComposite.bottom = new FormAttachment(100, -5);
			contentComposite.setLayoutData(fd_contentComposite);
		}
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 컨텐츠 컴포지트를 반환합니다.
	 * 
	 * @return 컨텐츠 컴포지트
	 */
	protected Composite getContentComposite() {
		if (title != null && !title.equals("")) {
			return contentComposite;
		} else {
			return this;
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void display(boolean progressBar) {
	}

	@Override
	public ActionIf[] getActions() {
		return new ActionIf[] {};
	}

}
