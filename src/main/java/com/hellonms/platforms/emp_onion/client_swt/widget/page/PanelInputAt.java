package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * PanelInputAt
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public abstract class PanelInputAt<T> extends PanelRound {

	public enum PANEL_INPUT_TYPE {
		CREATE, UPDATE, DELETE
	}

	/**
	 * 입력 판넬 리스너의 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface PanelInputListenerIf {

		/**
		 * 완료 상태가 변경되었을 경우 호출됩니다.
		 */
		public void completeChanged();

	}

	protected PANEL_INPUT_TYPE panelInputType;

	protected PanelInputListenerIf listener;

	protected String errorMessage;

	protected boolean complete;

	/**
	 * 모델
	 */
	protected T model;

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
	public PanelInputAt(Composite parent, int style, PANEL_INPUT_TYPE panelInputType, PanelInputListenerIf listener) {
		super(parent, style);

		this.panelInputType = panelInputType;
		this.listener = listener;
	}

	/**
	 * 완료 상태를 확인합니다.
	 */
	protected void checkComplete() {
		errorMessage = null;
		complete = true;

		completeChanged();
	}

	/**
	 * 완료 상태가 변경되었습니다.
	 * <p>
	 * 입력 판넬 리스너를 통해 완료 상태가 변경되었음을 알립니다.
	 * </p>
	 */
	protected void completeChanged() {
		listener.completeChanged();
	}

	/**
	 * 에러 메시지를 반환합니다.
	 * 
	 * @return 에러 메시지
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 에러 메시지를 설정합니다.
	 * 
	 * @param errorMessage
	 *            에러 메시지
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 완료 상태를 반환합니다.
	 * 
	 * @return 완료 상태
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * 완료 상태를 설정합니다.
	 * 
	 * @param complete
	 *            완료 상태
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	/**
	 * 모델을 반환합니다.
	 * 
	 * @return 모델
	 */
	abstract public T getModel();

	/**
	 * 모델을 설정합니다.
	 * 
	 * @param model
	 *            모델
	 */
	abstract public void setModel(T model);

}
