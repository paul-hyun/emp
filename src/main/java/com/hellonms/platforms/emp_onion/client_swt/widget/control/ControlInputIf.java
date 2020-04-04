package com.hellonms.platforms.emp_onion.client_swt.widget.control;

import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;

/**
 * <p>
 * ControlInputIf
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public interface ControlInputIf {

	/**
	 * 컨트롤을 반환합니다.
	 * 
	 * @return	컨트롤
	 */
	public Control getControl();

	/**
	 * 완료 상태를 반환합니다.
	 * 
	 * @return	완료 상태
	 */
	public boolean isComplete();

	/**
	 * 값을 반환합니다.
	 * 
	 * @return	값
	 */
	public Object getValue();

	/**
	 * 값을 설정합니다.
	 * 
	 * @param value	값
	 */
	public void setValue(Object value);

	/**
	 * 입력 판넬 리스트를 설정합니다.
	 * 
	 * @param listener	입력 판넬 리스너
	 */
	public void setPanelInputListener(PanelInputListenerIf listener);

}