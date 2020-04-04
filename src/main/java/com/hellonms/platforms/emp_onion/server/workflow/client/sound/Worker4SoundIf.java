/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.sound;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;

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
public interface Worker4SoundIf extends WorkerIf {

	/**
	 * <p>
	 * 사운드 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public byte[] querySound(EmpContext context, String path) throws EmpException;

}
