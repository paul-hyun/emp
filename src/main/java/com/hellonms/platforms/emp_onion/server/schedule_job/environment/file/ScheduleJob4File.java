/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.schedule_job.environment.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hellonms.platforms.emp_core.server.log.BlackBox;
import com.hellonms.platforms.emp_core.server.log.LEVEL;
import com.hellonms.platforms.emp_core.server.schedule_job.ScheduleJobIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Worker4PreferenceIf;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * 주기적으로 사용하지 않는 사용자 세션을 삭제한다.
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public class ScheduleJob4File implements ScheduleJobIf {

	private class FileGarbage {
		private final File file;
		private final long garbage_offset_milli;

		public FileGarbage(File file, int garbage_offset_day) {
			this.file = file;
			this.garbage_offset_milli = garbage_offset_day * 24L * 60L * 60L * 1000L;
		}
	}

	private class PreferenceGarbage {
		private final PREFERENCE_CODE preference_code;
		private final long garbage_offset_milli;

		public PreferenceGarbage(PREFERENCE_CODE preference_code, int garbage_offset_day) {
			this.preference_code = preference_code;
			this.garbage_offset_milli = garbage_offset_day * 24L * 60L * 60L * 1000L;
		}
	}

	private Worker4PreferenceIf worker4Preference;

	private List<FileGarbage> file_garbage_list = new ArrayList<FileGarbage>();

	private List<PreferenceGarbage> preference_garbage_list = new ArrayList<PreferenceGarbage>();

	private static final BlackBox blackBox = new BlackBox(ScheduleJob4File.class);

	@Override
	public Class<? extends ScheduleJobIf> getDefine_class() {
		return ScheduleJob4File.class;
	}

	public void addFileGarbage(File file, int garbage_offset_day) {
		file_garbage_list.add(new FileGarbage(file, garbage_offset_day));
	}

	public void addPreferenceGarbage(PREFERENCE_CODE preference_code, int garbage_offset_day) {
		preference_garbage_list.add(new PreferenceGarbage(preference_code, garbage_offset_day));
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void execute(EmpContext context, Date schedule_time) throws EmpException {
		for (FileGarbage file_garbage : file_garbage_list) {
			int delete_count = UtilFile.deleteFile(file_garbage.file, schedule_time.getTime() - file_garbage.garbage_offset_milli);
			if (0 < delete_count && blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, UtilString.format("delete file {} item from {}", delete_count, file_garbage.file.getAbsolutePath()));
			}
		}
		for (PreferenceGarbage preference_garbage : preference_garbage_list) {
			Model4Preference preference = worker4Preference.queryPreference(context, preference_garbage.preference_code);
			int delete_count = UtilFile.deleteFile(new File(preference.getPreferenceString()), schedule_time.getTime() - preference_garbage.garbage_offset_milli);
			if (0 < delete_count && blackBox.isEnabledFor(LEVEL.Fatal)) {
				blackBox.log(LEVEL.Fatal, context, UtilString.format("delete file {} item from {}", delete_count, preference.getPreferenceString()));
			}
		}
	}

}
