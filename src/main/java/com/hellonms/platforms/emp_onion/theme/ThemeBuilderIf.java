package com.hellonms.platforms.emp_onion.theme;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public interface ThemeBuilderIf {

	public interface ImageEnumIf {

	}

	public interface ColorEnumIf {

	}

	public interface FontEnumIf {

	}

	public interface CursorEnumIf {

	}

	public interface SoundEnumIf {

	}

	public final static int NONE = 0;

	public final static int BOLD = 1 << 0;

	public Image getImage(ImageEnumIf imageEnum);

	public Color getColor(ColorEnumIf colorEnum);

	public Font getFont(FontEnumIf fontEnum);

	public Cursor getCursor(CursorEnumIf cursorEnum);

	public byte[] getSound(SoundEnumIf soundEnum);

}
