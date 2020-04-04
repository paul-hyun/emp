package com.hellonms.platforms.emp_onion.theme;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;

public class ThemeBuilder4Onion implements ThemeBuilderIf {

	public enum IMAGE_ONION implements ImageEnumIf {
		APPLICATION_ICON_16, //
		APPLICATION_ICON_24, //
		APPLICATION_ICON_32, //
		APPLICATION_ICON_48, //
		APPLICATION_ICON_64, //
		APPLICATION_ICON_128, //
		APPLICATION_ICON_256, //
		BUTTON_LINK, //
		DIALOG_MSSAGE_CONFIRM_ICON, //
		DIALOG_MSSAGE_CONFIRM_TEXT, //
		DIALOG_MSSAGE_ERROR_ICON, //
		DIALOG_MSSAGE_ERROR_TEXT, //
		DIALOG_MSSAGE_INFO_ICON, //
		DIALOG_MSSAGE_INFO_TEXT, //
		DIALOG_PROGRESS_ICON, //
		MENU_STACK_MAIN_LEFT, //
		MENU_STACK_MAIN_MIDDLE, //
		MENU_STACK_MAIN_RIGHT, //
		MENU_STACK_SUB_LEFT, //
		MENU_STACK_SUB_MIDDLE, //
		MENU_STACK_SUB_RIGHT, //
		PAGE_ICON, //
		SELECTOR_PAGE_FIRST, //
		SELECTOR_PAGE_PREV, //
		SELECTOR_PAGE_NEXT, //
		SELECTOR_PAGE_LAST, //
		WIZARD_ICON, //
	}

	public enum COLOR_ONION implements ColorEnumIf {
		BUTTON_LINK_FG, //
		CALENDAR_WEEK_SUN, //
		CALENDAR_WEEK_SAT, //
		CALENDAR_SELECTED_BG, //
		CHART_RESOURCE_BG, //
		CHART_RESOURCE_GRID_FG, //
		CHART_LINE_BG, //
		CHART_LINE_FG, //
		CHART_LINE_GRID_FG, //
		DIALOG_BG, //
		LABEL_TEXT_MANDATORY_FG, //
		LABEL_TEXT_OPTIONAL_FG, //
		PAGE_BG, //
		PAGE_FG, //
		PAGE_TITLE_FG, //
		PAGE_DESCRIPTION_FG, //
		PANEL_BG, //
		PANEL_FG, //
		PANEL_CONTENTS_BORDER, //
		PANEL_CONTENTS_BG, //
		PANEL_ROUND_BORDER, //
		READ_WRITE, //
		READ_ONLY, //
		TEXT_INPUT_FG, //
		TEXT_INPUT_GUIDE_FG, //
		VIEW_BG, //
		VIEW_TAB_ACTIVE_BG1, //
		VIEW_TAB_ACTIVE_BG2, //
		VIEW_TAB_ACTIVE_FG, //
		VIEW_TAB_INACTIVE_BG1, //
		VIEW_TAB_INACTIVE_BG2, //
		VIEW_TAB_INACTIVE_FG, //
		WIDGET_READ_ONLY, //
		WIDGET_READ_WRITE, //
		WIZARD_BG, //
		WORKBENCH_BG, //
		WORKBENCH_FG, //
	}

	public enum FONT_ONION implements FontEnumIf {
		FIXED, //
		MESSAGE_BOX, //
		PAGE_TITLE, //
		PAGE_DESCRIPTION, //
		PANEL_TITLE, //
	}

	public enum CURSOR_ONION implements CursorEnumIf {
		HAND, //
	}

	@Override
	public Image getImage(ImageEnumIf imageEnum) {
		if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_16)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_16.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_24)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_24.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_32)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_32.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_48)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_48.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_64)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_64.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_128)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_128.png");
		} else if (imageEnum.equals(IMAGE_ONION.APPLICATION_ICON_256)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/emp_application_256.png");
		} else if (imageEnum.equals(IMAGE_ONION.BUTTON_LINK)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/button/button_link.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_CONFIRM_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_confirm_icon.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_CONFIRM_TEXT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_confirm_text.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_ERROR_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_error_icon.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_ERROR_TEXT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_error_text.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_INFO_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_info_icon.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_MSSAGE_INFO_TEXT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_message_info_text.png");
		} else if (imageEnum.equals(IMAGE_ONION.DIALOG_PROGRESS_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/dialog/dialog_progress_icon.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_MAIN_LEFT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_main_left.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_MAIN_MIDDLE)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_main_middle.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_MAIN_RIGHT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_main_right.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_SUB_LEFT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_sub_left.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_SUB_MIDDLE)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_sub_middle.png");
		} else if (imageEnum.equals(IMAGE_ONION.MENU_STACK_SUB_RIGHT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/menu/menu_stack_sub_right.png");
		} else if (imageEnum.equals(IMAGE_ONION.PAGE_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/page/page_icon.png");
		} else if (imageEnum.equals(IMAGE_ONION.SELECTOR_PAGE_FIRST)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/selector/selector_page_first.gif");
		} else if (imageEnum.equals(IMAGE_ONION.SELECTOR_PAGE_PREV)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/selector/selector_page_prev.gif");
		} else if (imageEnum.equals(IMAGE_ONION.SELECTOR_PAGE_NEXT)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/selector/selector_page_next.gif");
		} else if (imageEnum.equals(IMAGE_ONION.SELECTOR_PAGE_LAST)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/selector/selector_page_last.gif");
		} else if (imageEnum.equals(IMAGE_ONION.WIZARD_ICON)) {
			return UtilResource.getImage("/com/hellonms/platforms/emp_onion/theme/public/emp_onion/image/widget/wizard/wizard_icon.png");
		}
		return null;
	}

	@Override
	public Color getColor(ColorEnumIf colorEnum) {
		if (colorEnum.equals(COLOR_ONION.BUTTON_LINK_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.CALENDAR_WEEK_SUN)) {
			return UtilResource.getColor(new int[] { 255, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.CALENDAR_WEEK_SAT)) {
			return UtilResource.getColor(new int[] { 0, 0, 255 });
		} else if (colorEnum.equals(COLOR_ONION.CALENDAR_SELECTED_BG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.CHART_RESOURCE_BG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.CHART_RESOURCE_GRID_FG)) {
			return UtilResource.getColor(new int[] { 0, 100, 0 });
		} else if (colorEnum.equals(COLOR_ONION.CHART_LINE_BG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.CHART_LINE_FG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.CHART_LINE_GRID_FG)) {
			return UtilResource.getColor(new int[] { 208, 208, 208 });
		} else if (colorEnum.equals(COLOR_ONION.DIALOG_BG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.LABEL_TEXT_MANDATORY_FG)) {
			return UtilResource.getColor(new int[] { 128, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.LABEL_TEXT_OPTIONAL_FG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.PAGE_BG)) {
			return UtilResource.getColor(new int[] { 71, 71, 71 });
		} else if (colorEnum.equals(COLOR_ONION.PAGE_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.PAGE_TITLE_FG)) {
			return UtilResource.getColor(new int[] { 119, 153, 153 });
		} else if (colorEnum.equals(COLOR_ONION.PAGE_DESCRIPTION_FG)) {
			return UtilResource.getColor(new int[] { 208, 208, 208 });
		} else if (colorEnum.equals(COLOR_ONION.PANEL_BG)) {
			return UtilResource.getColor(new int[] { 113, 113, 113 });
		} else if (colorEnum.equals(COLOR_ONION.PANEL_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.PANEL_CONTENTS_BORDER)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.PANEL_CONTENTS_BG)) {
			return UtilResource.getColor(new int[] { 208, 208, 208 });
		} else if (colorEnum.equals(COLOR_ONION.PANEL_ROUND_BORDER)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.READ_WRITE)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.READ_ONLY)) {
			return UtilResource.getColor(new int[] { 238, 238, 238 });
		} else if (colorEnum.equals(COLOR_ONION.TEXT_INPUT_FG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.TEXT_INPUT_GUIDE_FG)) {
			return UtilResource.getColor(new int[] { 128, 128, 128 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_BG)) {
			return UtilResource.getColor(new int[] { 170, 170, 170 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_ACTIVE_BG1)) {
			return UtilResource.getColor(new int[] { 170, 170, 170 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_ACTIVE_BG2)) {
			return UtilResource.getColor(new int[] { 30, 30, 30 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_ACTIVE_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_INACTIVE_BG1)) {
			return UtilResource.getColor(new int[] { 190, 190, 190 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_INACTIVE_BG2)) {
			return UtilResource.getColor(new int[] { 190, 190, 190 });
		} else if (colorEnum.equals(COLOR_ONION.VIEW_TAB_INACTIVE_FG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.WIDGET_READ_ONLY)) {
			return UtilResource.getColor(new int[] {238, 238, 238 });
		} else if (colorEnum.equals(COLOR_ONION.WIDGET_READ_WRITE)) {
			return UtilResource.getColor(new int[] {255, 255, 255 });
		} else if (colorEnum.equals(COLOR_ONION.WIZARD_BG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.WORKBENCH_BG)) {
			return UtilResource.getColor(new int[] { 0, 0, 0 });
		} else if (colorEnum.equals(COLOR_ONION.WORKBENCH_FG)) {
			return UtilResource.getColor(new int[] { 255, 255, 255 });
		}
		return null;
	}

	@Override
	public Font getFont(FontEnumIf fontEnum) {
		if (fontEnum.equals(FONT_ONION.FIXED)) {
			return UtilResource.getFont(new Object[] { "GulimChe", 10, NONE, false, false });
		} else if (fontEnum.equals(FONT_ONION.MESSAGE_BOX)) {
			return UtilResource.getFont(new Object[] { "", 10, NONE, false, false });
		} else if (fontEnum.equals(FONT_ONION.PAGE_TITLE)) {
			return UtilResource.getFont(new Object[] { "", 12, BOLD, false, false });
		} else if (fontEnum.equals(FONT_ONION.PAGE_DESCRIPTION)) {
			return UtilResource.getFont(new Object[] { "", 10, NONE, false, false });
		} else if (fontEnum.equals(FONT_ONION.PANEL_TITLE)) {
			return UtilResource.getFont(new Object[] { "", 10, BOLD, false, false });
		}
		return null;
	}

	@Override
	public Cursor getCursor(CursorEnumIf cursorEnum) {
		if (cursorEnum.equals(CURSOR_ONION.HAND)) {
			return UtilResource.getCursor(SWT.CURSOR_HAND);
		}
		return null;
	}

	@Override
	public byte[] getSound(SoundEnumIf soundEnum) {
		return null;
	}

}
