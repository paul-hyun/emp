package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.data.shelf.DataShelfIf;
import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Panel4NetworkViewAt;

public class Panel4NetworkViewNeShelf extends Panel4NetworkViewAt {

	public class Figure4Bg extends FreeformLayeredPane {

		protected final Image shelfBg;

		public Figure4Bg(Image shelfBg) {
			this.shelfBg = shelfBg;
		}

		@Override
		public void paintFigure(Graphics graphics) {
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.fillRectangle(getBounds());
			if (shelfBg != null) {
				graphics.drawImage(shelfBg, 10, 10);
			}
		}

		public Point computeSize() {
			org.eclipse.swt.graphics.Rectangle bounds = shelfBg.getBounds();
			Point point = new Point(bounds.width + 20, bounds.height + 20);
			return point;
		}

		public void display(NODE node) {
			Map<Integer, Figure4Unit> oldUnitMap = new HashMap<Integer, Figure4Unit>();
			for (Object object : getChildren()) {
				if (object instanceof Figure4Unit) {
					Figure4Unit figure4Unit = (Figure4Unit) object;
					oldUnitMap.put(getFigure4UnitID(figure4Unit.row, figure4Unit.column), figure4Unit);
				}
			}

			int rowCount = dataShelf.getRowCount();
			for (int row = 0; row < rowCount; row++) {
				int columnCount = dataShelf.getColumnCount(row);
				for (int column = 0; column < columnCount; column++) {
					int figure4UnitID = getFigure4UnitID(row, column);
					Figure4Unit figure4Unit = oldUnitMap.remove(figure4UnitID);
					if (figure4Unit == null) {
						figure4Unit = new Figure4Unit(row, column);
						Figure4Bg.this.add(figure4Unit);
					}
					figure4Unit.unitBg = UtilResource.getImage(dataShelf.getImagePath(row, column));
					Point point = dataShelf.getImageLocation(row, column);
					figure4Unit.computeBounds(point.x, point.y);
				}
			}

			computeScroll(computeSize());
			Figure4Bg.this.repaint();
		}

		protected int getFigure4UnitID(int row, int column) {
			return row * 1000 + column;
		}
	}

	public class Figure4Unit extends Figure {

		protected final int row;

		protected final int column;

		public Image unitBg;

		public Figure4Unit(int row, int column) {
			this.row = row;
			this.column = column;
		}

		@Override
		public void paintFigure(Graphics graphics) {
			if (unitBg != null) {
				Rectangle bounds = Figure4Unit.this.getBounds();
				graphics.drawImage(unitBg, bounds.x, bounds.y);
			}
		}

		public void computeBounds(int x, int y) {
			org.eclipse.swt.graphics.Rectangle bounds = unitBg.getBounds();
			Figure4Unit.this.setBounds(new Rectangle(x, y, bounds.width, bounds.height));
		}

	}

	protected DataShelfIf dataShelf;

	protected Canvas canvas;

	protected LightweightSystem lightweightSystem;

	protected Figure4Bg figure4Bg;

	protected NODE node;

	public Panel4NetworkViewNeShelf(Composite parent, int style, DataShelfIf dataShelf, Panel4NetworkViewListenerIf listener) {
		super(parent, style, listener);

		this.dataShelf = dataShelf;

		createShelf();
	}

	@Override
	protected Control createContent(Composite parent) {
		canvas = new Canvas(parent, SWT.NONE);
		return canvas;
	}

	protected void createShelf() {
		lightweightSystem = new LightweightSystem(canvas);
		figure4Bg = new Figure4Bg(UtilResource.getImage(dataShelf.getBackgroundImage()));
		figure4Bg.setBackgroundColor(UtilResource.getColor(255, 255, 255));
		lightweightSystem.setContents(figure4Bg);
	}

	@Override
	protected MenuPopup4Network createMenuPopup4Network(Shell shell) {
		return null;
	}

	@Override
	protected NODE getNode() {
		return node;
	}

	@Override
	protected NODE getSelectedNode() {
		return node;
	}

	@Override
	public void display(boolean progressBar) {
	}

	@Override
	public void display(NODE node, boolean progressBar) {
		dataShelf.setDatas((Object) null);
		figure4Bg.display(node);
	}

	@Override
	public void monitor() {
	}

	@Override
	public void blink(boolean blink) {
	}

	@Override
	public void display(final NODE node, final Object object) {
	}

}
