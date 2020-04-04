/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.language;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.share.error.ERROR_CODE_ONION;

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
public class Worker4Language implements Worker4LanguageIf {

	private String[] language_folders = {};

	private LANGUAGE language = LANGUAGE.KOREAN;

	private Map<LANGUAGE, Map<String, String>> language_map = new HashMap<LANGUAGE, Map<String, String>>();

	public void setLanguage_folders(String... language_folders) {
		this.language_folders = language_folders;
	}

	public void setLanguage(LANGUAGE language) {
		this.language = language;
	}

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4LanguageIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
		UtilLanguage.setMessages(queryLanguage(context, language));
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public Map<String, String> queryLanguage(EmpContext context, LANGUAGE language) throws EmpException {
		Map<String, String> map = language_map.get(language);
		if (map == null) {
			map = loadLanguage(context, language);
			language_map.put(language, map);
		}
		return map;
	}

	private Map<String, String> loadLanguage(EmpContext context, LANGUAGE language) throws EmpException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			for (String language_folder : language_folders) {
				String language_resource = language_folder + "/language_" + language.getLocale() + ".properties";
				InputStream is = Worker4Language.class.getResourceAsStream(language_resource);
				Properties properties = new Properties();
				properties.load(is);
				is.close();
				for (Object key : properties.keySet()) {
					map.put((String) key, properties.getProperty((String) key));
				}
			}
			return map;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_ONION.FILE_IO, e);
		}
	}

}
