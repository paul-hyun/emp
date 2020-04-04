package com.hellonms.platforms.emp_onion.client_swt.widget.dialog;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PagePreference;

public class DialogPreference extends PreferenceDialog {

	public DialogPreference(Shell parentShell) {
		this(parentShell, new PreferenceManager());
	}

	public DialogPreference(Shell parentShell, PreferenceManager manager) {
		super(parentShell, manager);
	}

	public void addPagePreference(PagePreference pagePreference) {
		getPreferenceManager().addToRoot(new PreferenceNode(pagePreference.getId(), pagePreference));
	}

	public void addPagePreferences(PagePreference[] pagePreferences) {
		if (pagePreferences != null) {
			for (PagePreference pagePreference : pagePreferences) {
				addPagePreference(pagePreference);
			}
		}
	}

}
