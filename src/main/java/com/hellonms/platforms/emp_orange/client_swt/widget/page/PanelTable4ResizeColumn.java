package com.hellonms.platforms.emp_orange.client_swt.widget.page;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelTableIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPage;
import com.hellonms.platforms.emp_onion.client_swt.widget.selector.SelectorPage.SelectorPageListenerIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_util.string.UtilString;

public class PanelTable4ResizeColumn extends Composite implements PanelTableIf {

	private static class DataTableNull extends DataTableAt {

		@Override
		public int getColumnCount() {
			return 0;
		}

		@Override
		public String getColumnTitle(int column) {
			return "";
		}

		@Override
		public int getColumnStyle(int column) {
			return SWT.NONE;
		}

		@Override
		public int getColumnWidth(int column) {
			return 0;
		}

		@Override
		public Object getInput() {
			return this;
		}

		@Override
		public void clear() {
		}

		@Override
		public void setDatas(Object... datas) {
		}

		@Override
		public Object getData() {
			return null;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return new String[0];
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return "";
		}

		@Override
		public Color getForeground(Object element) {
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			return null;
		}

	}

	private static DataTableNull dataTableNull = new DataTableNull();

	/**
	 * 테이블 뷰어 리스너의 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface PanelTableListenerIf {

		/**
		 * 페이지 정보가 변경되었을 경우 호출됩니다.
		 * 
		 * @param startNo
		 *            페이지의 첫 항목 번호
		 * @param count
		 *            페이지 당 최대 항목 개수
		 */
		public void load(int startNo, int count);

	}

	private class ChildListener implements SelectorPageListenerIf {

		@Override
		public void load(int startNo, int count) {
			listener.load(startNo, count);
		}

	}

	private class TableColumnSorter extends ViewerComparator {

		public static final int ASC = 1;

		public static final int NONE = 0;

		public static final int DESC = -1;

		private int direction = 0;

		private TableColumn column;

		private int columnIndex;

		public TableColumnSorter(TableColumn column, int columnIndex) {
			this.column = column;
			this.columnIndex = columnIndex;

			this.column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (PanelTable4ResizeColumn.this.tableViewer.getComparator() != null) {
						if (PanelTable4ResizeColumn.this.tableViewer.getComparator() == TableColumnSorter.this) {
							int tdirection = TableColumnSorter.this.direction;
							if (tdirection == ASC) {
								setSorter(TableColumnSorter.this, DESC);
							} else if (tdirection == DESC) {
								setSorter(TableColumnSorter.this, NONE);
							}
						} else {
							setSorter(TableColumnSorter.this, ASC);
						}
					} else {
						setSorter(TableColumnSorter.this, ASC);
					}
				}
			});
		}

		public int compare(Viewer viewer, Object object1, Object object2) {
			return direction * doCompare(viewer, object1, object2);
		}

		protected int doCompare(Viewer TableViewer, Object object1, Object object2) {
			String string1 = dataTable.getColumnText(object1, columnIndex);
			String string2 = dataTable.getColumnText(object2, columnIndex);

			return UtilString.compare(string1, string2);
		}

		public void setSorter(TableColumnSorter tableColumnSorter, int direction) {
			if (direction == NONE) {
				column.getParent().setSortColumn(null);
				column.getParent().setSortDirection(SWT.NONE);
				PanelTable4ResizeColumn.this.tableViewer.setComparator(null);
			} else {
				column.getParent().setSortColumn(column);
				tableColumnSorter.direction = direction;
				if (direction == ASC) {
					column.getParent().setSortDirection(SWT.DOWN);
				} else {
					column.getParent().setSortDirection(SWT.UP);
				}
				if (PanelTable4ResizeColumn.this.tableViewer.getComparator() == tableColumnSorter) {
					PanelTable4ResizeColumn.this.refresh();
				} else {
					PanelTable4ResizeColumn.this.tableViewer.setComparator(tableColumnSorter);
				}
			}
		}

	}

	private SelectorPage selectorPage;

	private TableColumnLayout tableColumnLayout;

	protected TableViewer tableViewer;

	protected DataTableIf dataTable;

	private int style;

	private int rowCount;

	private PanelTableListenerIf listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public PanelTable4ResizeColumn(Composite parent, int style) {
		this(parent, style, 0, null);
	}

	public PanelTable4ResizeColumn(Composite parent, int style, DataTableIf dataTable) {
		this(parent, style);

		setDataTable(dataTable);
	}

	public PanelTable4ResizeColumn(Composite parent, int style, int rowCount, PanelTableListenerIf listener) {
		super(parent, SWT.NONE);
		this.style = style;
		this.rowCount = rowCount;
		this.listener = listener;

		createGUI();
	}

	public PanelTable4ResizeColumn(Composite parent, int style, int rowCount, DataTableIf dataTable, PanelTableListenerIf listener) {
		this(parent, style, rowCount, listener);

		setDataTable(dataTable);
	}

	private void createGUI() {
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent event) {
				dispose();
			}
		});

		setLayout(new FormLayout());

		if (0 < rowCount && listener != null) {
			selectorPage = new SelectorPage(this, SWT.NONE, rowCount, new ChildListener());
			FormData fd_pageNavigation = new FormData();
			fd_pageNavigation.bottom = new FormAttachment(100);
			fd_pageNavigation.left = new FormAttachment(0);
			fd_pageNavigation.right = new FormAttachment(100);
			selectorPage.setLayoutData(fd_pageNavigation);

			selectorPage.initialize();
		}

		Composite tableComposite = new Composite(this, SWT.NONE);
		FormData fd_tableComposite = new FormData();
		fd_tableComposite.right = new FormAttachment(100);
		fd_tableComposite.bottom = selectorPage == null ? new FormAttachment(100) : new FormAttachment(selectorPage, -5, SWT.TOP);
		fd_tableComposite.top = new FormAttachment(0);
		fd_tableComposite.left = new FormAttachment(0);
		tableComposite.setLayoutData(fd_tableComposite);

		tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		tableViewer = new TableViewer(tableComposite, style);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
	}

	/**
	 * 모델을 설정합니다.
	 * 
	 * @param model
	 *            테이블 모델
	 */
	public void setDataTable(DataTableIf dataTable) {
		if (this.dataTable != null) {
			this.dataTable.removePanelTable(this);
			for (TableColumn tableColumn : tableViewer.getTable().getColumns()) {
				tableColumn.dispose();
			}
			tableViewer.setContentProvider(PanelTable4ResizeColumn.dataTableNull);
			tableViewer.setLabelProvider(PanelTable4ResizeColumn.dataTableNull);
			tableViewer.setInput(PanelTable4ResizeColumn.dataTableNull.getInput());
		}
		this.dataTable = dataTable;

		for (int i = 0; i < dataTable.getColumnCount(); i++) {
			TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, dataTable.getColumnStyle(i));
			TableColumn tableColumn = tableViewerColumn.getColumn();
			tableColumn.setText(dataTable.getColumnTitle(i));
			tableColumnLayout.setColumnData(tableColumn, new ColumnWeightData(dataTable.getColumnWidth(i), dataTable.getColumnWidth(i)));

			newTableColumnSorter(tableColumn, i);
		}
		tableViewer.setContentProvider(this.dataTable);
		tableViewer.setLabelProvider(this.dataTable);
		tableViewer.setInput(this.dataTable.getInput());
		this.dataTable.addPanelTable(this);
	}

	protected ViewerComparator newTableColumnSorter(TableColumn column, int columnIndex) {
		return new TableColumnSorter(column, columnIndex);
	}

	@Override
	public void setBackground(Color color) {
		tableViewer.getTable().setBackground(color);
	}

	@Override
	public void setForeground(Color color) {
		tableViewer.getTable().setForeground(color);
	}

	@Override
	public void dispose() {
		if (this.dataTable != null) {
			this.dataTable.removePanelTable(this);
		}
		super.dispose();
	}

	@Override
	public void refresh() {
		tableViewer.refresh();
	}

	/**
	 * JFace의 테이블 뷰어를 반환합니다.
	 * 
	 * @return JFace의 테이블 뷰어
	 */
	public org.eclipse.jface.viewers.TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * 테이블의 모든 항목을 반환합니다.
	 * 
	 * @return 항목 배열
	 */
	public TableItem[] getItems() {
		return tableViewer.getTable().getItems();
	}

	/**
	 * 선택된 항목을 반환합니다.
	 * 
	 * @return 선택된 항목
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * 테이블 컬럼을 반환합니다.
	 * 
	 * @param index
	 *            컬럼의 위치
	 * @return 테이블 컬럼
	 */
	public Widget getColumn(int index) {
		return tableViewer.getTable().getColumn(index);
	}

	/**
	 * 테이블 리스너를 추가합니다.
	 * 
	 * @param eventType
	 *            이벤트 타입
	 * @param listener
	 *            리스너
	 */
	public void addTableListener(int eventType, Listener listener) {
		tableViewer.getTable().addListener(eventType, listener);
	}

	/**
	 * 선택 변경 리스너를 추가합니다.
	 * 
	 * @param listener
	 *            선택 변경 리스너
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		tableViewer.addSelectionChangedListener(listener);
	}

	/**
	 * 더블 클릭 리스너를 추가합니다.
	 * 
	 * @param listener
	 *            더블 클릭 리스너
	 */
	public void addDoubleClickListener(IDoubleClickListener listener) {
		tableViewer.addDoubleClickListener(listener);
	}

	/**
	 * 테이블 페이지를 그립니다.
	 * 
	 * @param pageConfig
	 *            테이블 페이지 정보
	 */
	public void display(TablePageConfig<?> pageConfig) {
		if (selectorPage != null) {
			if (pageConfig != null) {
				selectorPage.display(pageConfig);
			} else {
				selectorPage.initialize();
			}
		}
	}

	/**
	 * 현재 페이지의 첫 항목 번호를 반환한다.
	 * 
	 * @return 항목 번호
	 */
	public int getStartNo() {
		return selectorPage == null ? 0 : selectorPage.getStartNo();
	}

	public DataTableIf getDataTable() {
		return dataTable;
	}

	public void setDatas(Object... datas) {
		dataTable.setDatas(datas);
	}

}
