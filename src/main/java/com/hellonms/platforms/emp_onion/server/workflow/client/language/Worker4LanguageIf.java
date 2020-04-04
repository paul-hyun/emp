/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.language;

import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;

/**
 * <p>
 * Image Worker
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 12.
 * @modified 2015. 5. 12.
 * @author cchyun
 *
 */
public interface Worker4LanguageIf extends WorkerIf {

	/**
	 * <p>
	 * 언어값 조회
	 * </p>
	 * 
	 * @param context
	 * @param language
	 * @return
	 * @throws EmpException
	 */
	public Map<String, String> queryLanguage(EmpContext context, LANGUAGE language) throws EmpException;

}
