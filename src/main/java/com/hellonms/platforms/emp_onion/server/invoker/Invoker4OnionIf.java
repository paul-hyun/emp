/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.invoker;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import com.hellonms.platforms.emp_core.server.invoker.InvokerIf;
import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.LANGUAGE;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.Model4Preference;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_onion.share.model.help.about.Model4About;

/**
 * <p>
 * Orange Invoker 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 10.
 * @modified 2015. 3. 10.
 * @author cchyun
 * 
 */
public interface Invoker4OnionIf extends InvokerIf {

	/**
	 * <p>
	 * EMP 정보를 조회한다.
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public Model4About getAbout(EmpContext context) throws EmpException;

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

	/**
	 * <p>
	 * 이미지 추가
	 * </p>
	 * 
	 * @param context
	 * @param path
	 * @param filename
	 * @param filedata
	 * @throws EmpException
	 */
	public void createImage(EmpContext context, String path, String filename, byte[] filedata) throws EmpException;

	/**
	 * <p>
	 * 이미지 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(EmpContext context, String path) throws EmpException;

	/**
	 * <p>
	 * 이미지 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(EmpContext context, String path, int width, int height) throws EmpException;

	/**
	 * <p>
	 * 이미지 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @param width
	 * @param height
	 * @param color
	 * @return
	 * @throws EmpException
	 */
	public byte[] queryImage(EmpContext context, String path, int width, int height, Color color) throws EmpException;

	/**
	 * <p>
	 * 이미지 목록 조회
	 * </p>
	 * 
	 * @param context
	 * @param path
	 * @param extensions
	 * @return
	 * @throws EmpException
	 */
	public String[] queryListImagePath(EmpContext context, String path, String[] extensions) throws EmpException;

	/**
	 * <p>
	 * sound 파일 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public byte[] querySound(EmpContext context, String path) throws EmpException;

	/**
	 * <p>
	 * Preference 조회
	 * </p>
	 *
	 * @param context
	 * @param preference_code
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference queryPreference(EmpContext context, PREFERENCE_CODE preference_code) throws EmpException;

	/**
	 * <p>
	 * Preference 조회
	 * </p>
	 *
	 * @param context
	 * @param function_group
	 * @param function
	 * @param preference
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] queryListPreference(EmpContext context, String function_group, String function, String preference) throws EmpException;

	/**
	 * <p>
	 * Preference 수정
	 * </p>
	 *
	 * @param context
	 * @param preference
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference updatePreference(EmpContext context, Model4Preference preference) throws EmpException;

	/**
	 * <p>
	 * Preference 수정
	 * </p>
	 *
	 * @param context
	 * @param preferences
	 * @return
	 * @throws EmpException
	 */
	public Model4Preference[] updateListPreference(EmpContext context, Model4Preference[] preferences) throws EmpException;

	/**
	 * <p>
	 * DB 백업
	 * </p>
	 *
	 * @param context
	 * @return
	 * @throws EmpException
	 */
	public File backupDatabaseByUser(EmpContext context) throws EmpException;

}
