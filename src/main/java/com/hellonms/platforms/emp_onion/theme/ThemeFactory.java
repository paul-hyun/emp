package com.hellonms.platforms.emp_onion.theme;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf.ColorEnumIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf.CursorEnumIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf.FontEnumIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf.ImageEnumIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf.SoundEnumIf;

public class ThemeFactory {

	private static LinkedList<ThemeBuilderIf> themeBuilderList = new LinkedList<ThemeBuilderIf>();

	public static Image getImage(ImageEnumIf imageEnum) {
		for (ThemeBuilderIf themeBuilder : themeBuilderList) {
			Image image = themeBuilder.getImage(imageEnum);
			if (image != null) {
				return image;
			}
		}
		return UtilResource.getImage("");
	}

	public static Color getColor(ColorEnumIf colorEnum) {
		for (ThemeBuilderIf themeBuilder : themeBuilderList) {
			Color color = themeBuilder.getColor(colorEnum);
			if (color != null) {
				return color;
			}
		}
		return UtilResource.getColor(new int[] { 0, 0, 0 });
	}

	public static Font getFont(FontEnumIf fontEnum) {
		for (ThemeBuilderIf themeBuilder : themeBuilderList) {
			Font font = themeBuilder.getFont(fontEnum);
			if (font != null) {
				return font;
			}
		}
		return UtilResource.getFont(new Object[] { "", 10, ThemeBuilderIf.NONE, false, false });
	}

	public static Cursor getCursor(CursorEnumIf cursorEnum) {
		for (ThemeBuilderIf themeBuilder : themeBuilderList) {
			Cursor cursor = themeBuilder.getCursor(cursorEnum);
			if (cursor != null) {
				return cursor;
			}
		}
		return UtilResource.getCursor(SWT.CURSOR_ARROW);
	}

	public static byte[] getSound(SoundEnumIf soundEnum) {
		for (ThemeBuilderIf themeBuilder : themeBuilderList) {
			byte[] sound = themeBuilder.getSound(soundEnum);
			if (sound != null) {
				return sound;
			}
		}
		return UtilResource.getSound("");
	}

	public static void addThemeBuilder(ThemeBuilderIf panelBuilder) {
		themeBuilderList.addFirst(panelBuilder);
	}

}
