/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.image;

import java.awt.Color;

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
public interface Worker4ImageIf extends WorkerIf {

	/**
	 * <p>
	 * 이미지 생성
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
	 * 이미지 조회
	 * </p>
	 *
	 * @param context
	 * @param path
	 * @return
	 * @throws EmpException
	 */
	public String[] queryListImagePath(EmpContext context, String path, String[] extensions) throws EmpException;

}
