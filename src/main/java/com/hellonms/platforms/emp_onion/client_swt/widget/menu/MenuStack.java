package com.hellonms.platforms.emp_onion.client_swt.widget.menu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * MenuStack
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class MenuStack extends Composite {

	/**
	 * 메뉴 아이템 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class MenuItem {

		private Composite parent;

		private String text;

		private Color colorSelected;

		private Color colorUnselected;

		private Image imageLeft;

		private Image imageMiddle;

		private Image imageRight;

		private boolean selected;

		private Rectangle rect = new Rectangle(0, 0, 0, 0);

		private int marginX;

		private int shiftY;

		private int marginy;

		private SelectionListener listener;

		/**
		 * 생성자 입니다.
		 * 
		 * @param parent
		 *            부모 컴포지트
		 * @param style
		 *            스타일
		 */
		public MenuItem(Composite parent, int style) {
			this.parent = parent;
		}

		/**
		 * 생성자 입니다.
		 * 
		 * @param parent
		 *            부모 컴포지트
		 * @param style
		 *            스타일
		 * @param text
		 *            메뉴 이름
		 * @param shiftY
		 *            글자의 Y축 이동거리
		 * @param colorSelected
		 *            선택 시 색
		 * @param colorUnselected
		 *            선택 해제 시 색
		 * @param listener
		 *            선택 리스너
		 */
		public MenuItem(Composite parent, int style, String text, int shiftY, Color colorSelected, Color colorUnselected, SelectionListener listener) {
			this(parent, style, text, shiftY, colorSelected, colorUnselected, null, null, null, listener);
		}

		/**
		 * 생성자 입니다.
		 * 
		 * @param parent
		 *            부모 컴포지트
		 * @param style
		 *            스타일
		 * @param text
		 *            메뉴 이름
		 * @param shiftY
		 *            글자의 Y축 이동거리
		 * @param colorSelected
		 *            선택 시 색
		 * @param colorUnselected
		 *            선택 해제 시 색
		 * @param imageLeft
		 *            왼쪽 이미지
		 * @param imageMiddle
		 *            중간 이미지
		 * @param imageRight
		 *            오른쪽 이미지
		 * @param listener
		 *            선택 리스너
		 */
		public MenuItem(Composite parent, int style, String text, int shiftY, Color colorSelected, Color colorUnselected, Image imageLeft, Image imageMiddle, Image imageRight, SelectionListener listener) {
			this.parent = parent;
			this.text = text;
			this.shiftY = shiftY;
			this.colorSelected = colorSelected;
			this.colorUnselected = colorUnselected;
			this.imageLeft = imageLeft;
			this.imageMiddle = imageMiddle;
			this.imageRight = imageRight;
			this.listener = listener;

			updateBounds();
		}

		/**
		 * 컨트롤을 그립니다.
		 * 
		 * @param e
		 *            그리기 이벤트
		 */
		public void paintControl(PaintEvent e) {
			if (selected & imageLeft != null && imageMiddle != null && imageRight != null) {
				Rectangle rectLeft = imageLeft.getBounds();
				Rectangle rectMiddle = imageMiddle.getBounds();
				Rectangle rectRight = imageRight.getBounds();

				int x = rect.x;
				int y = rect.y;
				e.gc.drawImage(imageLeft, x, y);
				x += rectLeft.width;
				e.gc.drawImage(imageMiddle, 0, 0, rectMiddle.width, rectMiddle.height, x, y, rect.width - rectLeft.width - rectRight.width, rectMiddle.height);
				x += (rect.width - rectLeft.width - rectRight.width);
				e.gc.drawImage(imageRight, x, y);
			}

			e.gc.setForeground(selected ? colorSelected : colorUnselected);
			e.gc.drawText(text, rect.x + marginX, rect.y + marginy, true);
		}

		/**
		 * 메뉴 이름을 반환합니다.
		 * 
		 * @return 메뉴 이름
		 */
		public String getText() {
			return text;
		}

		/**
		 * 메뉴 이름을 설정합니다.
		 * 
		 * @param text
		 *            메뉴 이름
		 */
		public void setText(String text) {
			this.text = text;

			updateBounds();
		}

		/**
		 * 메뉴 아이템의 넓이를 반환합니다.
		 * 
		 * @return 메뉴 아이템의 넓이
		 */
		public Rectangle getBounds() {
			return rect;
		}

		private void updateBounds() {
			GC gc = new GC(parent.getShell());
			Point size = gc.textExtent(text);
			gc.dispose();

			rect.width = size.x + 16;
			marginX = 8;
			rect.height = 28;
			marginy = (28 - size.y) / 2 + shiftY;
		}

		/**
		 * 메뉴 아이템의 위치를 설정합니다.
		 * 
		 * @param x
		 *            메뉴 아이템의 왼쪽 모서리의 X 좌표
		 * @param y
		 *            메뉴 아이템의 왼쪽 모서리의 Y 좌표
		 */
		public void setLocation(int x, int y) {
			rect.x = x;
			rect.y = y;
		}

		/**
		 * 선택 상태를 반환합니다.
		 * 
		 * @return 선택 상태
		 */
		public boolean isSelected() {
			return selected;
		}

		/**
		 * 선택 상태를 설정합니다.
		 * 
		 * @param selected
		 *            선택 상태
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;
			if (selected && listener != null) {
				listener.widgetSelected(null);
			}
		}

	}

	private static final Image imageLevel1Left = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_MAIN_LEFT);

	private static final Image imageLevel1Middle = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_MAIN_MIDDLE);

	private static final Image imageLevel1Right = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_MAIN_RIGHT);

	private static final Image imageLevel2Left = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_SUB_LEFT);

	private static final Image imageLevel2Middle = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_SUB_MIDDLE);

	private static final Image imageLevel2Right = ThemeFactory.getImage(IMAGE_ONION.MENU_STACK_SUB_RIGHT);

	private static final Color colorLevel1Selected = UtilResource.getColor(255, 255, 255);

	private static final Color colorLevel1Unselected = UtilResource.getColor(214, 214, 214);

	private static final Color colorLevel2Selected = UtilResource.getColor(255, 255, 255);

	private static final Color colorLevel2Unselected = UtilResource.getColor(0, 0, 0);

	Map<String, List<MenuItem>> menuItemMap = new LinkedHashMap<String, List<MenuItem>>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public MenuStack(Composite parent, int style) {
		super(parent, style);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle rect = getBounds();
				if (0 < menuItemMap.size() && 8 < rect.width) {
					Rectangle rectLevel2Left = imageLevel2Left.getBounds();
					Rectangle rectLevel2Middle = imageLevel2Middle.getBounds();
					Rectangle rectLevel2Right = imageLevel2Right.getBounds();

					int x = 0;
					int y = 23;
					e.gc.drawImage(imageLevel2Left, x, y);
					x += rectLevel2Left.width;
					e.gc.drawImage(imageLevel2Middle, 0, 0, rectLevel2Middle.width, rectLevel2Middle.height, x, y, rect.width - rectLevel2Left.width - rectLevel2Right.width, rectLevel2Middle.height);
					x += (rect.width - rectLevel2Left.width - rectLevel2Right.width);
					e.gc.drawImage(imageLevel2Right, x, y);

					for (List<MenuItem> menuItemList : menuItemMap.values()) {
						MenuItem level1Item = menuItemList.get(0);
						level1Item.paintControl(e);

						if (level1Item.isSelected()) {
							for (int i = 1; i < menuItemList.size(); i++) {
								menuItemList.get(i).paintControl(e);
							}
						}
					}
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				List<MenuItem> menuItemListOld = null;
				List<MenuItem> menuItemListNew = null;

				for (List<MenuItem> menuItemList : menuItemMap.values()) {
					if (menuItemList.get(0).getBounds().contains(e.x, e.y)) {
						menuItemListNew = menuItemList;
						break;
					} else if (menuItemList.get(0).isSelected()) {
						menuItemListOld = menuItemList;
					}
				}
				if (menuItemListNew != null) {
					MenuItem selectedLevel1Item = menuItemListNew.get(0);
					if (selectedLevel1Item.listener == null) {
						for (List<MenuItem> menuItemList : menuItemMap.values()) {
							MenuItem level1Item = menuItemList.get(0);
							level1Item.setSelected(false);
						}
						selectedLevel1Item.setSelected(true);
					} else {
						selectedLevel1Item.listener.widgetSelected(null);
					}
				} else if (menuItemListOld != null) {
					MenuItem level2ItemSelected = null;
					for (int i = 1; i < menuItemListOld.size(); i++) {
						if (menuItemListOld.get(i).getBounds().contains(e.x, e.y)) {
							level2ItemSelected = menuItemListOld.get(i);
							break;
						}
					}

					if (level2ItemSelected != null) {
						for (int i = 1; i < menuItemListOld.size(); i++) {
							menuItemListOld.get(i).setSelected(menuItemListOld.get(i) == level2ItemSelected);
						}
					}
				}

				if (!isDisposed()) {
					redraw();
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public Rectangle computeTrim(int x, int y, int width, int height) {
		int max_width = 0;

		int level1_width = 8;
		for (List<MenuItem> menuItemList : menuItemMap.values()) {
			MenuItem level1Item = menuItemList.get(0);

			level1Item.setLocation(level1_width, 0);
			level1_width += (level1Item.getBounds().width + 8);

			int level2_width = 8;
			for (int i = 1; i < menuItemList.size(); i++) {
				MenuItem level2Item = menuItemList.get(i);

				level2Item.setLocation(level2_width, 23);
				level2_width += (level2Item.getBounds().width + 8);
			}
			max_width = Math.max(max_width, level2_width);
		}
		max_width = Math.max(max_width, level1_width);

		return new Rectangle(x, y, max_width, 52);
	}

	/**
	 * 메뉴를 추가합니다.
	 * 
	 * @param path
	 *            메뉴의 경로
	 * @param listener
	 *            선택 리스너
	 */
	public void addMenu(final String[] path, SelectionListener listener) {
		if (0 < path.length && path.length <= 2) {
			List<MenuItem> menuItemList = menuItemMap.get(path[0]);
			if (menuItemList == null) {
				menuItemList = new ArrayList<MenuItem>();
				menuItemMap.put(path[0], menuItemList);

				if (1 < path.length) {
					menuItemList.add(new MenuItem(this, SWT.NONE, path[0], -2, colorLevel1Selected, colorLevel1Unselected, imageLevel1Left, imageLevel1Middle, imageLevel1Right, null));
					if (menuItemMap.size() == 1) {
						menuItemList.get(0).setSelected(true);
					}
				}
			}

			if (path.length == 1) {
				menuItemList.add(new MenuItem(this, SWT.NONE, path[0], -2, colorLevel1Selected, colorLevel1Unselected, imageLevel1Left, imageLevel1Middle, imageLevel1Right, listener));
			} else if (path.length == 2) {
				menuItemList.add(new MenuItem(this, SWT.NONE, path[1], 0, colorLevel2Selected, colorLevel2Unselected, listener));
			}
		}
	}

}
