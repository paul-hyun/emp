package com.hellonms.platforms.emp_onion.client_swt.util.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_util.string.UtilString;

public class UtilResource {

	protected static Map<String, Image> imageMap = new HashMap<String, Image>();

	protected static Map<RGB, Color> colorMap = new HashMap<RGB, Color>();

	protected static Map<String, Font> fontMap = new HashMap<String, Font>();

	protected static Map<Integer, Cursor> cursorMap = new HashMap<Integer, Cursor>();

	protected static Map<String, byte[]> soundMap = new HashMap<String, byte[]>();

	/**
	 * 이미지를 반환합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @return 이미지
	 */
	public static Image getImage(String path) {
		Image image = imageMap.get(path);
		if (image == null) {
			try {
				InputStream stream = UtilResource.class.getResourceAsStream(path);
				if (stream != null) {
					image = getImage(stream);
				} else {
					System.err.println("no image: " + path);
					image = getMissingImage();
				}
				imageMap.put(path, image);
			} catch (Exception e) {
				e.printStackTrace();
				image = getMissingImage();
				imageMap.put(path, image);
			}
		}
		return image;
	}

	/**
	 * 이미지의 중복여부를 반환합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @return 중복여부
	 */
	public static boolean containsImage(String path) {
		return imageMap.containsKey(path);
	}

	/**
	 * 이미지를 반환합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @param width
	 *            이미지의 너비
	 * @param height
	 *            이미지의 높이
	 * @return 이미지
	 */
	public static Image getImage(String path, int width, int height) {
		String key = UtilString.format("{}_{}_{}", path, width, height);
		Image image = imageMap.get(key);

		if (image == null) {
			Image srcImage = getImage(path);
			image = new Image(srcImage.getDevice(), srcImage.getImageData().scaledTo(width, height));

			imageMap.put(key, image);
		}

		return image;
	}

	/**
	 * 이미지를 반환합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @param color
	 *            이미지의 배경색
	 * @param alpha
	 *            이미지의 투명도
	 * @return 이미지
	 */
	public static Image getImage(String path, Color color, int alpha) {
		String key = UtilString.format("{}_{}_{}_{}_{}", path, color.getRed(), color.getGreen(), color.getBlue(), alpha);
		Image image = imageMap.get(key);

		if (image == null) {
			Image srcImage = getImage(path);
			image = new Image(srcImage.getDevice(), srcImage.getImageData());
			Rectangle rect = image.getBounds();

			GC gc = new GC(image);
			gc.setAlpha(alpha);
			gc.setBackground(color);
			gc.fillRectangle(rect);
			gc.dispose();

			imageMap.put(key, image);
		}

		return image;
	}

	/**
	 * 이미지를 반환합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @param width
	 *            이미지의 너비
	 * @param height
	 *            이미지의 높이
	 * @param color
	 *            이미지의 배경색
	 * @param alpha
	 *            이미지의 투명도
	 * @return 이미지
	 */
	public static Image getImage(String path, int width, int height, Color color, int alpha) {
		String key = UtilString.format("{}_{}_{}_{}_{}_{}_{}", path, width, height, color.getRed(), color.getGreen(), color.getBlue(), alpha);
		Image image = imageMap.get(key);

		if (image == null) {
			Image srcImage = getImage(path);
			image = new Image(srcImage.getDevice(), srcImage.getImageData().scaledTo(width, height));
			Rectangle rect = image.getBounds();

			GC gc = new GC(image);
			gc.setAlpha(alpha);
			gc.setBackground(color);
			gc.fillRectangle(rect);
			gc.dispose();

			imageMap.put(key, image);
		}

		return image;
	}

	/**
	 * 이미지를 저장합니다.
	 * 
	 * @param path
	 *            이미지 파일의 경로
	 * @param data
	 *            이미지 데이터
	 * @return 이미지 객체
	 */
	public static Image putImage(String path, byte[] data) {
		Image image;
		try {
			image = getImage(new ByteArrayInputStream(data));
			imageMap.put(path, image);
		} catch (IOException e) {
			e.printStackTrace();
			image = getMissingImage();
			imageMap.put(path, image);
		}
		return image;
	}

	/**
	 * 이미지를 반환합니다.
	 * 
	 * @param stream
	 *            이미지를 입력 받는 스트림
	 * @return 이미지
	 * @throws IOException
	 */
	protected static Image getImage(InputStream stream) throws IOException {
		try {
			Display display = Display.getCurrent();
			ImageData data = new ImageData(stream);
			if (data.transparentPixel > 0) {
				return new Image(display, data, data.getTransparencyMask());
			}
			return new Image(display, data);
		} finally {
			stream.close();
		}
	}

	protected static Image getMissingImage() {
		Image image = new Image(Display.getCurrent(), 16, 16);

		GC gc = new GC(image);
		gc.setBackground(getColor(SWT.COLOR_RED));
		gc.fillRectangle(0, 0, 16, 16);
		gc.dispose();

		return image;
	}

	/**
	 * 색을 반환합니다.
	 * 
	 * @param systemColorID
	 *            시스템에 지정된 색의 아이디
	 * @return 색
	 */
	public static Color getColor(int systemColorID) {
		Display display = Display.getCurrent();
		return display.getSystemColor(systemColorID);
	}

	/**
	 * 색을 반환합니다.
	 * 
	 * @param rgb
	 *            rgb 색의 배열
	 * @return 색
	 */
	public static Color getColor(int[] rgb) {
		return getColor(new RGB(rgb[0], rgb[1], rgb[2]));
	}

	/**
	 * 색을 반환합니다.
	 * 
	 * @param r
	 *            레드의 값
	 * @param g
	 *            그린의 값
	 * @param b
	 *            불루의 값
	 * @return 색
	 */
	public static Color getColor(int r, int g, int b) {
		return getColor(new RGB(r, g, b));
	}

	/**
	 * 색을 반환합니다.
	 * 
	 * @param rgb
	 *            RGB 객체
	 * @return 색
	 */
	public static Color getColor(RGB rgb) {
		Color color = colorMap.get(rgb);
		if (color == null) {
			Display display = Display.getCurrent();
			color = new Color(display, rgb);
			colorMap.put(rgb, color);
		}
		return color;
	}

	/**
	 * 폰트를 반환합니다.
	 * 
	 * @param font
	 *            폰트 배열
	 * @return 폰트
	 */
	public static Font getFont(Object[] font) {
		return getFont((String) font[0], (Integer) font[1], (Integer) font[2], (Boolean) font[3], (Boolean) font[4]);
	}

	/**
	 * 폰트를 반환합니다.
	 * 
	 * @param name
	 *            폰트의 이름
	 * @param size
	 *            폰트의 크기
	 * @param style
	 *            폰트의 스타일
	 * @param strikeout
	 *            취소선 표시여부
	 * @param underline
	 *            밑줄 표시여부
	 * @return
	 */
	public static Font getFont(String name, int size, int style, boolean strikeout, boolean underline) {
		String fontName = UtilString.format("{}_{}_{}_{}_{}", name, size, style, strikeout, underline);
		Font font = fontMap.get(fontName);
		if (font == null) {
			FontData fontData = new FontData(name, size, style);
			if (strikeout || underline) {
				try {
					Class<?> logFontClass = Class.forName("org.eclipse.swt.internal.win32.LOGFONT");
					Object logFont = FontData.class.getField("data").get(fontData);
					if (logFont != null && logFontClass != null) {
						if (strikeout) {
							logFontClass.getField("lfStrikeOut").set(logFont, Byte.valueOf((byte) 1));
						}
						if (underline) {
							logFontClass.getField("lfUnderline").set(logFont, Byte.valueOf((byte) 1));
						}
					}
				} catch (Exception e) {
					System.err.println("Unable to set underline or strikeout" + " (probably on a non-Windows platform). " + e);
				}
			}
			font = new Font(Display.getCurrent(), fontData);
			fontMap.put(fontName, font);
		}
		return font;
	}

	/**
	 * 커서를 반환합니다.
	 * 
	 * @param id
	 *            커서의 스타일
	 * @return 커서
	 */
	public static Cursor getCursor(int id) {
		Integer key = Integer.valueOf(id);
		Cursor cursor = cursorMap.get(key);
		if (cursor == null) {
			cursor = new Cursor(Display.getDefault(), id);
			cursorMap.put(key, cursor);
		}
		return cursor;
	}

	public static byte[] getSound(final String path) {
		if (!soundMap.containsKey(path)) {
			try {
				byte[] sound = (byte[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
					@Override
					public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
						return invoker.querySound(request, path);
					}
				});
				soundMap.put(path, sound);
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return soundMap.get(path);
	}

}
