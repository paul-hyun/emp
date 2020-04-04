package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;

/**
 * <p>
 * PanelInput4NeAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class PanelInput4NeAt extends PanelInputAt<Model4Ne> {

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
	public PanelInput4NeAt(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style, panelInputType, listener);
	}

	/**
	 * NE 그룹 아이디를 설정합니다.
	 * 
	 * @param ne_group_id
	 *            NE 그룹 아이디
	 */
	public abstract void setNe_group_id(int ne_group_id);

	/**
	 * NE IP 수정 가능여부를 설정합니다.
	 * 
	 * @param editable
	 *            수정 가능여부
	 */
	public abstract void setHostEditable(boolean editable);

	/**
	 * NE IP를 반환합니다.
	 * 
	 * @return NE IP
	 */
	public abstract String getHost();

}
