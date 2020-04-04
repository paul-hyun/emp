/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.server.workflow.client.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.server.workflow.WorkerIf;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.share.error.ERROR_CODE_ONION;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.file.UtilFile;
import com.hellonms.platforms.emp_util.string.UtilString;

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
public class Worker4Image implements Worker4ImageIf {

	private class ImageValue {

		private final BufferedImage image;

		private final byte[] buffer;

		public ImageValue(BufferedImage image, byte[] buffer) {
			this.image = image;
			this.buffer = buffer;
		}

	}

	private Map<String, ImageValue> imageMap = new HashMap<String, ImageValue>();

	@Override
	public Class<? extends WorkerIf> getDefine_class() {
		return Worker4ImageIf.class;
	}

	@Override
	public void initialize(EmpContext context) throws EmpException {
	}

	@Override
	public void dispose(EmpContext context) throws EmpException {
	}

	@Override
	public void createImage(EmpContext context, String path, String filename, byte[] filedata) throws EmpException {
		File dir = new File(EmpContext.getEMP_HOME(), path);
		if (dir.isDirectory()) {
			boolean exists = false;
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					if (Arrays.equals(UtilFile.readFile(file), filedata)) {
						exists = true;
						break;
					}
				}
			}
			if (!exists) {
				UtilFile.saveFile(filedata, new File(dir, UtilString.format("{}_{}", UtilDate.format(UtilDate.MILLI_FORMAT_TRIM), filename.replaceAll(" ", ""))));
			}
		}
	}

	@Override
	public byte[] queryImage(EmpContext context, String path) throws EmpException {
		try {
			ImageValue image_new = imageMap.get(path);
			if (image_new == null) {
				File file = new File(EmpContext.getEMP_HOME(), path);

				byte[] buffer = UtilFile.readFile(file);
				ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
				BufferedImage image = ImageIO.read(bais);
				bais.close();

				image_new = new ImageValue(image, buffer);
				imageMap.put(path, image_new);
			}
			return image_new.buffer;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_ONION.FILE_IO, e);
		}
	}

	@Override
	public byte[] queryImage(EmpContext context, String path, int width, int height) throws EmpException {
		try {
			String key = UtilString.format("{}.{}.{}", path, width, height);
			ImageValue image_new = imageMap.get(key);
			if (image_new == null) {
				ImageValue image_org = imageMap.get(path);
				if (image_org == null) {
					File file = new File(EmpContext.getEMP_HOME(), path);

					byte[] buffer = UtilFile.readFile(file);
					ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
					BufferedImage image = ImageIO.read(bais);
					bais.close();

					image_org = new ImageValue(image, buffer);
					imageMap.put(path, image_org);
				}

				BufferedImage image = new BufferedImage(width, height, image_org.image.getType());
				Graphics2D g = image.createGraphics();
				g.drawImage(image_org.image, 0, 0, width, height, null);
				g.dispose();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				byte[] buffer = baos.toByteArray();
				baos.close();

				image_new = new ImageValue(image, buffer);
				imageMap.put(key, image_new);
			}
			return image_new.buffer;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_ONION.FILE_IO, e);
		}
	}

	@Override
	public byte[] queryImage(EmpContext context, String path, int width, int height, Color color) throws EmpException {
		try {
			String key = UtilString.format("{}.{}.{}.{}.{}.{}.{}", path, width, height, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
			ImageValue image_new = imageMap.get(key);
			if (image_new == null) {
				ImageValue image_org = imageMap.get(path);
				if (image_org == null) {
					File file = new File(EmpContext.getEMP_HOME(), path);

					byte[] buffer = UtilFile.readFile(file);
					ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
					BufferedImage image = ImageIO.read(bais);
					bais.close();

					image_org = new ImageValue(image, buffer);
					imageMap.put(path, image_org);
				}

				BufferedImage image = new BufferedImage(width, height, image_org.image.getType());
				Graphics2D g = image.createGraphics();
				g.drawImage(image_org.image, 0, 0, width, height, null);
				g.dispose();
				{ // change image tone
					int[] rgb1s = image.getRGB(0, 0, width, height, null, 0, width);

					g = image.createGraphics();
					g.setBackground(color);
					g.setColor(color);
					g.fillRect(0, 0, width, height);
					g.dispose();

					int index = 0;
					for (int y = 0; y < height; y++) {
						for (int x = 0; x < width; x++) {
							int rgb2 = image.getRGB(x, y);
							int rgb3 = (rgb1s[index] & 0xFF000000) + (rgb2 & 0x00FFFFFF);
							image.setRGB(x, y, rgb3);
							index++;
						}
					}
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				byte[] buffer = baos.toByteArray();
				baos.close();

				image_new = new ImageValue(image, buffer);
				imageMap.put(key, image_new);
			}
			return image_new.buffer;
		} catch (IOException e) {
			throw new EmpException(ERROR_CODE_ONION.FILE_IO, e);
		}
	}

	@Override
	public String[] queryListImagePath(EmpContext context, String path, String[] extensions) throws EmpException {
		File dir = new File(EmpContext.getEMP_HOME(), path);
		List<String> path_list = new ArrayList<String>();
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					boolean ok = false;
					for (String extension : extensions) {
						if (file.getName().endsWith(UtilString.format("{}", extension))) {
							ok = true;
							break;
						}
					}
					if (ok) {
						if (path.endsWith("/")) {
							path_list.add(UtilString.format("{}{}", path, file.getName()));
						} else {
							path_list.add(UtilString.format("{}/{}", path, file.getName()));
						}
					}
				}
			}
		}
		return path_list.toArray(new String[0]);
	}

}
