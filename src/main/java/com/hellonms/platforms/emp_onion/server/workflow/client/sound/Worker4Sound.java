/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_util.file.UtilFile;

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
public class Worker4Sound implements Worker4SoundIf {

	private class SoundValue {

		private final byte[] buffer;

		public SoundValue(byte[] buffer) {
			this.buffer = buffer;
		}

	}

	private Map<String, SoundValue> imageMap = new HashMap<String, SoundValue>();

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4SoundIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public byte[] querySound(EmpContext context, String path) throws EmpException {
		SoundValue image_new = imageMap.get(path);
		if (image_new == null) {
			File file = new File(EmpContext.getEMP_HOME(), path);

			byte[] buffer = UtilFile.readFile(file);

			image_new = new SoundValue(buffer);
			imageMap.put(path, image_new);
		}
		return image_new.buffer;
	}

}
