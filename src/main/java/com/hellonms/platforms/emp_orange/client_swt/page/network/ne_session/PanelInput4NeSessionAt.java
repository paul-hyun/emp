package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;

/**
 * <p>
 * PanelInput4NeSessionAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class PanelInput4NeSessionAt extends PanelInputAt<Model4NeSessionIf> {

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            입력 판넬 리스너
	 */
	public PanelInput4NeSessionAt(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);
	}

}
