package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * TextConsole
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextConsole extends StyledText {

	/**
	 * 텍스트 콘솔 항목의 일반적인 속성을 정의한 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface TextConsoleItemIf {

		/**
		 * 텍스트를 반환합니다.
		 * 
		 * @return 텍스트
		 */
		public String getText();

		/**
		 * 텍스트의 길이를 반환합니다.
		 * 
		 * @return 텍스트의 길이
		 */
		public int getTextLength();

		/**
		 * 특정 범위의 스타일 집합을 반환합니다.
		 * 
		 * @param start
		 *            시작 위치
		 * @return 특정 범위의 스타일 집합
		 */
		public StyleRange[] getStyleRanges(int start);

		/**
		 * 선택된 위치를 반환합니다.
		 * 
		 * @param start
		 *            시작 위치
		 * @return 위치
		 */
		public Point getSelection(int start);

	}

	private int text_length;

	private int max_item_count;

	private int upper_item_count;

	private List<TextConsoleItemIf> itemList = new ArrayList<TextConsoleItemIf>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param max_item_count
	 *            최대 아이템 개수
	 */
	public TextConsole(Composite parent, int style, int max_item_count) {
		super(parent, style);
		// super.setSelectionBackground(SWTResourceManager.getColor(0, 0, 0));

		this.max_item_count = max_item_count;
		this.upper_item_count = (int) (max_item_count * 1.1);
	}

	/**
	 * 텍스트 콘솔 아이템을 추가합니다.
	 * 
	 * @param item
	 *            텍트스 콘솔 아이템
	 */
	public void add(TextConsoleItemIf item) {
		int start = text_length;
		String text = item.getText();

		append(text);
		for (StyleRange styleRange : item.getStyleRanges(start)) {
			setStyleRange(styleRange);
		}
		setSelection(item.getSelection(start));

		text_length += text.length();
		itemList.add(item);

		if (upper_item_count < itemList.size()) {
			int delete_length = 0;
			while (max_item_count < itemList.size()) {
				TextConsoleItemIf remove = itemList.remove(0);
				delete_length += remove.getTextLength();
			}
			replaceTextRange(0, delete_length, "");
			text_length -= delete_length;
		}
	}

}
