package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne_group;

import java.util.List;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;

public class Panel4NetworkViewNeGroupGrid extends Panel4NetworkViewNeGroup {

	public class Figure4BgGrid extends Figure4Bg {

		/**
		 * 공간 크기
		 */
		protected static final int SPACE = 4;

		/**
		 * 제목 너비
		 */
		protected static final int TITLE_WIDTH = 24;

		/**
		 * 제목 높이
		 */
		protected static final int TITLE_HEIGHT = 24;

		/**
		 * NE 높이
		 */
		protected static final int NE_HEIGHT = 16;

		/**
		 * NE 너비
		 */
		protected int ne_width = 8;

		/**
		 * 컬럼 개수
		 */
		protected int columnCount = 50;

		/**
		 * 로우 개수
		 */
		public int rowCount;

		public Figure4BgGrid() {
			super();
		}

		@Override
		public void paintFigure(Graphics graphics) {
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.fillRectangle(getBounds());

			int nodeCount = getChildren().size();
			Rectangle bounds = getBounds();

			int columnCount = 50;
			this.columnCount = columnCount;

			int ne_width = (bounds.width - (TITLE_WIDTH + SPACE * 2 + 16)) / columnCount - SPACE;
			if (ne_width < 8) {
				ne_width = 8;
			}
			this.ne_width = ne_width;

			int rowCount = (bounds.height - (TITLE_HEIGHT + SPACE * 2)) / (NE_HEIGHT + SPACE) - 1;
			rowCount -= rowCount % 5;
			if (rowCount < 5) {
				rowCount = 5;
			}
			this.rowCount = rowCount;

			if (columnCount * rowCount < nodeCount) {
				rowCount = nodeCount / columnCount + (nodeCount % columnCount == 0 ? 2 : 1);
				setBounds(new Rectangle(bounds.x, bounds.y, bounds.width, TITLE_HEIGHT + SPACE * 2 + rowCount * (NE_HEIGHT + SPACE)));
			}

			int grid_width = columnCount * (ne_width + SPACE);
			int grid_height = rowCount * (NE_HEIGHT + SPACE);

			graphics.setBackgroundColor(UtilResource.getColor(192, 192, 192));
			graphics.fillRectangle(SPACE + TITLE_WIDTH + SPACE, SPACE, grid_width + 8, TITLE_HEIGHT);
			graphics.fillRectangle(SPACE, SPACE + TITLE_HEIGHT + SPACE, TITLE_WIDTH, grid_height + 8);

			for (int column = 0; column < columnCount; column++) {
				int x = TITLE_WIDTH + SPACE * 2 + (column + 1) * (ne_width + SPACE) - (SPACE / 2);
				int y1 = TITLE_HEIGHT - 2;
				int y2 = y1 + 4;
				graphics.setLineStyle(SWT.LINE_SOLID);
				graphics.setForegroundColor(UtilResource.getColor(0, 0, 0));
				graphics.drawLine(x, y1, x, y2);

				if (column % 5 == 4) {
					String text = String.valueOf(column + 1);
					graphics.setForegroundColor(UtilResource.getColor(0, 0, 0));
					graphics.setFont(NODE_FONT);
					Dimension size = FigureUtilities.getTextExtents(text, NODE_FONT);
					graphics.drawText(text, x - size.width / 2, y1 - size.height - 2);

					graphics.setLineStyle(SWT.LINE_SOLID);
					graphics.setForegroundColor(UtilResource.getColor(192, 192, 192));
					graphics.drawLine(x, y2, x, TITLE_HEIGHT + SPACE + grid_height + 2);
				}
			}

			for (int row = 0; row < rowCount; row++) {
				int x1 = TITLE_WIDTH - 2;
				int x2 = x1 + 4;
				int y = TITLE_HEIGHT + SPACE * 2 + (row + 1) * (NE_HEIGHT + SPACE) - (SPACE / 2);
				graphics.setLineStyle(SWT.LINE_SOLID);
				graphics.setForegroundColor(UtilResource.getColor(0, 0, 0));
				graphics.drawLine(x1, y, x2, y);

				if (row % 5 == 4) {
					String text = String.valueOf(row + 1);
					graphics.setForegroundColor(UtilResource.getColor(0, 0, 0));
					graphics.setFont(NODE_FONT);
					Dimension size = FigureUtilities.getTextExtents(text, NODE_FONT);
					graphics.drawText(text, x1 - size.width - 2, y - size.height / 2 + 2);

					graphics.setForegroundColor(UtilResource.getColor(192, 192, 192));
					graphics.setLineStyle(SWT.LINE_SOLID);
					graphics.drawLine(x2, y, TITLE_WIDTH + SPACE + grid_width + 2, y);
				}
			}

			for (int row = 0; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					graphics.setForegroundColor(UtilResource.getColor(128, 128, 128));
					graphics.setLineStyle(SWT.LINE_SOLID);
					graphics.drawRectangle(TITLE_WIDTH + SPACE * 2 + column * (ne_width + SPACE), TITLE_HEIGHT + SPACE * 2 + row * (NE_HEIGHT + SPACE), ne_width, NE_HEIGHT);
				}
			}

			layoutNodes();
		}

		@Override
		public void display(NODE node) {
			try {
				Model4NeGroup neGroup = (Model4NeGroup) node.getValue();

				int r = 0xFF & neGroup.getNe_group_map_bg_color() >> 16;
				int g = 0xFF & neGroup.getNe_group_map_bg_color() >> 8;
				int b = 0xFF & neGroup.getNe_group_map_bg_color() >> 0;
				setBackgroundColor(UtilResource.getColor(r, g, b));

				// CHILD 생성 (존재하는 것은 skip)
				NODE[] childs = ModelClient4NetworkTree.getInstance().getListChild(neGroup.getNe_group_id());

				// 기존 figure에 저장
				List<?> list = getChildren();
				int listSize = list.size();
				int childsLength = childs.length;
				for (int i = 0; i < listSize && i < childsLength; i++) {
					Figure4Node figureNode = (Figure4Node) list.get(i);
					figureNode.setValue(childs[i]);
				}

				// 새로운 노드 생성
				for (int i = listSize; i < childsLength; i++) {
					Figure4Node figureNode = createFigure4Node(childs[i]);
					add(figureNode);
				}

				// 기존 노드 삭제
				for (int i = listSize - 1; i > childsLength - 1; i--) {
					Figure4Node figureNode = (Figure4Node) list.get(i);
					remove(figureNode);
				}

				layoutNodes();
				computeScroll(computeSize());
				repaint();
			} catch (EmpException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 노드의 레이아웃을 배치합니다.
		 */
		public void layoutNodes() {
			int index = 0;
			for (Object object : getChildren()) {
				if (object instanceof Figure4Node) {
					Figure4Node figureNode = (Figure4Node) object;
					int column = index % columnCount;
					int row = index / columnCount;
					figureNode.setBounds(new Rectangle(TITLE_WIDTH + SPACE * 2 + column * (ne_width + SPACE), TITLE_HEIGHT + SPACE * 2 + row * (NE_HEIGHT + SPACE), ne_width, NE_HEIGHT));

					index++;
				}
			}
		}

		/**
		 * 화면크기를 계산합니다.
		 * 
		 * @return 화면크기
		 */
		public org.eclipse.swt.graphics.Point computeSize() {
			int rowCount = getChildren().size() / columnCount + 1;
			if (rowCount < 5) {
				rowCount = 5;
			}
			return new org.eclipse.swt.graphics.Point(TITLE_WIDTH + SPACE * 2 + columnCount * (8 + SPACE) + 16, TITLE_HEIGHT + SPACE * 2 + rowCount * (NE_HEIGHT + SPACE) + 16);
		}

	}

	public class Figure4NodeGrid extends Figure4Node {

		/**
		 * 생성자 입니다.
		 * 
		 * @param node
		 *            노드
		 */
		public Figure4NodeGrid(NODE node) {
			super(node);
		}

		@Override
		public void initFigure() {
		}

		@Override
		public void paintFigure(Graphics graphics) {
			Rectangle bounds = getBounds();

			if (icon_blink) {
				graphics.setBackgroundColor(UtilResource.getColor(224, 224, 224));
			} else {
				graphics.setBackgroundColor(UtilResource4Orange.getColor(node.getSeverity()));
			}
			graphics.fillRectangle(bounds);

			if (selected) {
				graphics.setForegroundColor(NODE_SELECTED);
				graphics.drawRectangle(new Rectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1));
			}
		}

		/**
		 * 선택상태를 설정합니다.
		 * 
		 * @param selected
		 *            선택상태
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;
			if (selected && node != null) {
				node.stopIconBlink(false);
				this.icon_blink = false;
			}
		}

	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4NetworkViewNeGroupGrid(Composite parent, int style, Panel4NetworkViewNeGroupListenerIf listener) {
		super(parent, style, listener);
	}

	@Override
	protected Figure4Bg createFigure4Bg() {
		return new Figure4BgGrid();
	}

	@Override
	protected Figure4Node createFigure4Node(NODE node) {
		return new Figure4NodeGrid(node);
	}

}
