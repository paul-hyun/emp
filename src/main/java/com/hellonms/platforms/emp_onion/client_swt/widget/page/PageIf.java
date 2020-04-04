package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import com.hellonms.platforms.emp_onion.client_swt.widget.action.ActionIf;

/**
 * <p>
 * PageIf
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public interface PageIf {

	/**
	 * 화면을 그립니다.
	 * 
	 * @param progressBar
	 *            프로그래스바 사용여부
	 */
	public void display(boolean progressBar);

	public String getTitle();

	/**
	 * <p>
	 * toolbar에 출력할 Action 목록을 출력합니다.
	 * </p>
	 *
	 * @return
	 */
	public ActionIf[] getActions();

}
