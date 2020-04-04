package com.hellonms.platforms.emp_orange.client_swt.page.security.user;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.share.model.security.user.Model4User;
import com.hellonms.platforms.emp_orange.share.model.security.user_group.Model4UserGroup;

/**
 * <p>
 * PanelInput4UserAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class PanelInput4UserAt extends PanelInputAt<Model4User> {

	public PanelInput4UserAt(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);
	}

	public abstract void setModel4UserGroups(Model4UserGroup[] model4UserGroup);
	
	public abstract void setModel4NeGroups(Model4NeGroup[] model4NeGroups);

}
