package com.hellonms.platforms.emp_orange.theme;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilderIf;

public class ThemeBuilder4Orange implements ThemeBuilderIf {

	public enum IMAGE_ORANGE implements ImageEnumIf {
		LONGIN, TOOLBAR_BG, //
		TOOLBAR_ALARM_CF, //
		TOOLBAR_ALARM_CRITICAL, //
		TOOLBAR_ALARM_MAJOR, //
		TOOLBAR_ALARM_MINOR, //
		TOOLBAR_SPEAKER_ON, //
		TOOLBAR_SPEAKER_PAUSE, //
		TOOLBAR_SPEAKER_OFF, //
		TOOLBAR_CI, //
		NETWORK_BUTTON_EDIT_DISABLED, //
		NETWORK_BUTTON_EDIT_DOWN, //
		NETWORK_BUTTON_EDIT_NORMAL, //
		NETWORK_BUTTON_EDIT_OVER, //
		NETWORK_BUTTON_LINK_DISABLED, //
		NETWORK_BUTTON_LINK_DOWN, //
		NETWORK_BUTTON_LINK_NORMAL, //
		NETWORK_BUTTON_LINK_OVER, //
		NETWORK_BUTTON_ITEM_DISABLED, //
		NETWORK_BUTTON_ITEM_DOWN, //
		NETWORK_BUTTON_ITEM_NORMAL, //
		NETWORK_BUTTON_ITEM_OVER, //
		NETWORK_BUTTON_SAVE_DISABLED, //
		NETWORK_BUTTON_SAVE_DOWN, //
		NETWORK_BUTTON_SAVE_NORMAL, //
		NETWORK_BUTTON_SAVE_OVER, //
		FAULT_ALARM_CF, //
		FAULT_ALARM_CF_ACK, //
		FAULT_ALARM_CF_FILTER_ON, //
		FAULT_ALARM_CF_FILTER_OFF, //
		FAULT_ALARM_CRITICAL, //
		FAULT_ALARM_CRITICAL_ACK, //
		FAULT_ALARM_CRITICAL_FILTER_ON, //
		FAULT_ALARM_CRITICAL_FILTER_OFF, //
		FAULT_ALARM_MAJOR, //
		FAULT_ALARM_MAJOR_ACK, //
		FAULT_ALARM_MAJOR_FILTER_ON, //
		FAULT_ALARM_MAJOR_FILTER_OFF, //
		FAULT_ALARM_MINOR, //
		FAULT_ALARM_MINOR_ACK, //
		FAULT_ALARM_MINOR_FILTER_ON, //
		FAULT_ALARM_MINOR_FILTER_OFF, //
		FAULT_ALARM_CLEAR, //
		FAULT_ALARM_CLEAR_ACK, //
		FAULT_ALARM_CLEAR_FILTER_ON, //
		FAULT_ALARM_CLEAR_FILTER_OFF, //
		FAULT_ALARM_INFO, //
		FAULT_ALARM_INFO_ACK, //
		FAULT_ALARM_INFO_FILTER_ON, //
		FAULT_ALARM_INFO_FILTER_OFF, //
		FAULT_ALARM_CONSOLE_RESET, //
		ABOUT_CI, //
	}

	public enum COLOR_ORANGE implements ColorEnumIf {
		TOOLBAR_ALARM_FG, //
		NETWORK_ICON_SELECTED_BORDER, //
		NETWORK_ICON_UNSELECTED_BORDER, //
		ALARM_CONSOLE_CLEARED, //
		ALARM_CONSOLE_ACKED, //
		ALARM_CLEARED, //
		ALARM_ACKED, //
		FAULT_ALARM_CF, //
		FAULT_ALARM_CRITICAL, //
		FAULT_ALARM_MAJOR, //
		FAULT_ALARM_MINOR, //
		FAULT_ALARM_CLEAR, //
		FAULT_ALARM_WARNING, //
		FAULT_ALARM_INFO, //
		CONSOLE_TREE_BG, //
		CONSOLE_TREE_FG, //
		CONSOLE_TABLE_BG, //
		CONSOLE_TABLE_FG, //
	}

	public enum FONT_ORANGE implements FontEnumIf {
		TOOLBAR_ALARM, //
		NETWORK_MAP_NODE_NAME, //
	}

	public enum SOUND_ORANGE implements SoundEnumIf {
		FAULT_ALARM_CF, //
		FAULT_ALARM_CRITICAL, //
		FAULT_ALARM_MAJOR, //
		FAULT_ALARM_MINOR, //
		FAULT_ALARM_CLEAR, //
		FAULT_ALARM_WARNING, //
		FAULT_ALARM_INFO, //
	}

	@Override
	public Image getImage(ImageEnumIf imageEnum) {
		if (imageEnum.equals(IMAGE_ORANGE.LONGIN)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/security/login.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_BG)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_bg.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_ALARM_CF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_cf.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_ALARM_CRITICAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_critical.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_ALARM_MAJOR)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_major.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_ALARM_MINOR)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_minor.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_SPEAKER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_sound_on.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_SPEAKER_PAUSE)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_sound_pause.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_SPEAKER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_sound_off.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.TOOLBAR_CI)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/toolbar/toolbar_ci.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DISABLED)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_edit_disabled.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_DOWN)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_edit_down.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_NORMAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_edit_normal.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_EDIT_OVER)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_edit_over.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_LINK_DISABLED)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_link_disabled.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_LINK_DOWN)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_link_down.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_LINK_NORMAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_link_normal.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_LINK_OVER)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_link_over.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_DISABLED)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_item_disabled.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_DOWN)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_item_down.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_NORMAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_item_normal.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_ITEM_OVER)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_item_over.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DISABLED)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_save_disabled.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_DOWN)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_save_down.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_NORMAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_save_normal.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.NETWORK_BUTTON_SAVE_OVER)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/network/button_save_over.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/cf_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CF_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/cf_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/cf_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CF_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/cf_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CRITICAL)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/critical_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/critical_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/critical_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CRITICAL_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/critical_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MAJOR)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/major_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MAJOR_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/major_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/major_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MAJOR_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/major_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MINOR)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/minor_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MINOR_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/minor_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/minor_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_MINOR_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/minor_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CLEAR)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/clear_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CLEAR_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/clear_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/clear_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CLEAR_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/clear_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_INFO)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/info_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_INFO_ACK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/info_ack_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_ON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/info_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_INFO_FILTER_OFF)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/info_BOX_X_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.FAULT_ALARM_CONSOLE_RESET)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/fault/reset_BOX_16.png");
		} else if (imageEnum.equals(IMAGE_ORANGE.ABOUT_CI)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_orange/theme/public/emp_orange/image/page/help/about.png");
		}
		return null;
	}

	@Override
	public Color getColor(ColorEnumIf colorEnum) {
		if (colorEnum.equals(COLOR_ORANGE.TOOLBAR_ALARM_FG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ORANGE.NETWORK_ICON_SELECTED_BORDER)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ORANGE.NETWORK_ICON_UNSELECTED_BORDER)) {
			return UtilResource.getColor(new int[] { 220, 220, 220 });
		} else if (colorEnum.equals(COLOR_ORANGE.ALARM_CONSOLE_CLEARED)) {
			return UtilResource.getColor(new int[] { 115, 115, 115 });
		} else if (colorEnum.equals(COLOR_ORANGE.ALARM_CONSOLE_ACKED)) {
			return UtilResource.getColor(new int[] { 117, 117, 117 });
		} else if (colorEnum.equals(COLOR_ORANGE.ALARM_CLEARED)) {
			return UtilResource.getColor(new int[] { 253, 253, 253 });
		} else if (colorEnum.equals(COLOR_ORANGE.ALARM_ACKED)) {
			return UtilResource.getColor(new int[] { 251, 251, 251 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_CF)) {
			return UtilResource.getColor(new int[] { 128, 128, 128 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_CRITICAL)) {
			return UtilResource.getColor(new int[] { 255, 0, 0 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_MAJOR)) {
			return UtilResource.getColor(new int[] { 255, 128, 0 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_MINOR)) {
			return UtilResource.getColor(new int[] { 217, 196, 49 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_CLEAR)) {
			return UtilResource.getColor(new int[] { 98, 223, 55 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_WARNING)) {
			return UtilResource.getColor(new int[] { 135, 90, 45 });
		} else if (colorEnum.equals(COLOR_ORANGE.FAULT_ALARM_INFO)) {
			return UtilResource.getColor(new int[] { 108, 165, 166 });
		} else if (colorEnum.equals(COLOR_ORANGE.CONSOLE_TREE_BG)) {
			return UtilResource.getColor(new int[] { 71, 71, 71 });
		} else if (colorEnum.equals(COLOR_ORANGE.CONSOLE_TREE_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ORANGE.CONSOLE_TABLE_BG)) {
			return UtilResource.getColor(new int[] { 71, 71, 71 });
		} else if (colorEnum.equals(COLOR_ORANGE.CONSOLE_TABLE_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		}
		return null;
	}

	@Override
	public Font getFont(FontEnumIf fontEnum) {
		if (fontEnum.equals(FONT_ORANGE.TOOLBAR_ALARM)) {
			return UtilResource.getFont(new Object[] { "", 14, BOLD, false, false });
		} else if (fontEnum.equals(FONT_ORANGE.NETWORK_MAP_NODE_NAME)) {
			return UtilResource.getFont(new Object[] { "", 9, NONE, false, false });
		}
		return null;
	}

	@Override
	public Cursor getCursor(CursorEnumIf cursorEnum) {
		return null;
	}

	@Override
	public byte[] getSound(SoundEnumIf soundEnum) {
		if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_CF)) {
			return UtilResource.getSound("/data/sound/fault/communicationFail.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_CRITICAL)) {
			return UtilResource.getSound("/data/sound/fault/critical.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_MAJOR)) {
			return UtilResource.getSound("/data/sound/fault/major.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_MINOR)) {
			return UtilResource.getSound("/data/sound/fault/minor.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_CLEAR)) {
			return UtilResource.getSound("/data/sound/fault/clear.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_WARNING)) {
			return UtilResource.getSound("/data/sound/fault/warning.wav");
		} else if (soundEnum.equals(SOUND_ORANGE.FAULT_ALARM_INFO)) {
			return UtilResource.getSound("/data/sound/fault/info.wav");
		}
		return null;
	}

}
