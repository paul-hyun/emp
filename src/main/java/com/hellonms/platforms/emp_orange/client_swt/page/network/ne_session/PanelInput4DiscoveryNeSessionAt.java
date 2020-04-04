package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionDiscoveryFilterIf;

public abstract class PanelInput4DiscoveryNeSessionAt extends PanelInputAt<Model4NeSessionDiscoveryFilterIf> {

	public PanelInput4DiscoveryNeSessionAt(Composite parent, int style, PanelInputListenerIf listener) {
		super(parent, style, null, listener);
	}

}
