/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_core.share.model;

import java.io.Serializable;

/**
 * <p>
 * 플랫푬 표준 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 9.
 * @modified 2015. 3. 9.
 * @author cchyun
 * 
 */
public interface ModelIf extends Serializable {

	public static final String S_LB = " {";

	public static final String S_RB = "}";

	public static final String S_NL = "\n";

	public static final String S_TB = "\t";

	public static final String S_DL = " : ";

	public static final String S_LA = "[ ";

	public static final String S_RA = "]";

	/**
	 * <p>
	 * 모델 복사본을 생성한다.
	 * </p>
	 * 
	 * @return
	 */
	public ModelIf copy();

	/**
	 * <p>
	 * 모델을 문자열로 표현한다.
	 * </p>
	 * 
	 * @param indent
	 * @return
	 */
	public String toString(String indent);

	/**
	 * <p>
	 * 모델의 내용을 사용자가 인지 할 수 있는 형태로 출력한다.
	 * </p>
	 * 
	 * @param indent
	 * @return
	 */
	public String toDisplayString(String indent);

}
