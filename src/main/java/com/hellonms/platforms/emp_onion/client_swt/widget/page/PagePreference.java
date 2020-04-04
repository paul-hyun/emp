package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class PagePreference extends PreferencePage {

	private String id;

	public PagePreference(String title, String id) {
		super(title);

		this.id = id;
	}

	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	public String getId() {
		return id;
	}

}
