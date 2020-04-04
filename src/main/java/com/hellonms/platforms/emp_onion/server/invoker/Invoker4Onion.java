/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.invoker;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.invoker.InvokerIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkflowMap;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_onion.server.workflow.client.image.Worker4ImageIf;
import com.hellonms.platforms.emp_onion.server.workflow.client.language.Worker4LanguageIf;
import com.hellonms.platforms.emp_onion.server.workflow.client.sound.Worker4SoundIf;
import com.hellonms.platforms.emp_onion.server.workflow.environment.database.Worker4DatabaseIf;
import com.hellonms.platforms.emp_onion.server.workflow.environment.preference.Worker4PreferenceIf;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;

/**
 * <p>
 * Orange Invoker 구현
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public class Invoker4Onion implements Invoker4OnionIf {

	private Model4About about;

	private boolean ready = false;

	@Override
	public Model4About getAbout(EmpContext context) throws EmpException {
		if (about == null) {
			about = newAbout(context);
		}
		return about;
	}

	protected Model4About newAbout(EmpContext context) {
		Model4About about = new Model4About();
		about.setManufacturer("Hello NMS");
		about.setOui("EMP");
		about.setProduct_class("EMP_ONION");
		return about;
	}

	@Override
	public Class<? extends InvokerIf> getDefine_class() {
		return Invoker4OnionIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		ready = true;
	}

	@Override
	public boolean isReady(EmpContext context) throws EmpException {
		return ready;
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
		ready = false;
	}

	@Override
	public Map<String, String> queryLanguage(EmpContext context, LANGUAGE language) throws EmpException {
		Worker4LanguageIf worker4Language = (Worker4LanguageIf) WorkflowMap.getWorker(Worker4LanguageIf.class);
		return worker4Language.queryLanguage(context, language);
	}

	@Override
	public void createImage(EmpContext context, String path, String filename, byte[] filedata) throws EmpException {
		Worker4ImageIf worker4Image = (Worker4ImageIf) WorkflowMap.getWorker(Worker4ImageIf.class);
		worker4Image.createImage(context, path, filename, filedata);
	}

	@Override
	public byte[] queryImage(EmpContext context, String path) throws EmpException {
		Worker4ImageIf worker4Image = (Worker4ImageIf) WorkflowMap.getWorker(Worker4ImageIf.class);
		return worker4Image.queryImage(context, path);
	}

	@Override
	public byte[] queryImage(EmpContext context, String path, int width, int height) throws EmpException {
		Worker4ImageIf worker4Image = (Worker4ImageIf) WorkflowMap.getWorker(Worker4ImageIf.class);
		return worker4Image.queryImage(context, path, width, height);
	}

	@Override
	public byte[] queryImage(EmpContext context, String path, int width, int height, Color color) throws EmpException {
		Worker4ImageIf worker4Image = (Worker4ImageIf) WorkflowMap.getWorker(Worker4ImageIf.class);
		return worker4Image.queryImage(context, path, width, height, color);
	}

	@Override
	public String[] queryListImagePath(EmpContext context, String path, String[] extensions) throws EmpException {
		Worker4ImageIf worker4Image = (Worker4ImageIf) WorkflowMap.getWorker(Worker4ImageIf.class);
		return worker4Image.queryListImagePath(context, path, extensions);
	}

	@Override
	public byte[] querySound(EmpContext context, String path) throws EmpException {
		Worker4SoundIf worker4Sound = (Worker4SoundIf) WorkflowMap.getWorker(Worker4SoundIf.class);
		return worker4Sound.querySound(context, path);
	}

	@Override
	public Model4Preference queryPreference(EmpContext context, PREFERENCE_CODE preference_code) throws EmpException {
		Worker4PreferenceIf worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
		return worker4Preference.queryPreference(context, preference_code);
	}

	@Override
	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException {
		Worker4PreferenceIf worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
		return worker4Preference.queryListPreference(context, function_group, function, preference);
	}

	@Override
	public Model4Preference updatePreference(EmpContext context, Model4Preference preference) throws EmpException {
		Worker4PreferenceIf worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
		return worker4Preference.updatePreference(context, preference);
	}

	@Override
	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException {
		Worker4PreferenceIf worker4Preference = (Worker4PreferenceIf) WorkflowMap.getWorker(Worker4PreferenceIf.class);
		return worker4Preference.updateListPreference(context, preferences);
	}

	@Override
	public File backupDatabaseByUser(EmpContext context) throws EmpException {
		Worker4DatabaseIf worker4Database = (Worker4DatabaseIf) WorkflowMap.getWorker(Worker4DatabaseIf.class);
		return worker4Database.backupDatabaseByUser(context);
	}

}
