package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableIf;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;

public interface PanelTableIf {

	public void setBackground(Color color);

	public void setForeground(Color color);

	public void dispose();

	public void refresh();

	public TableViewer getTableViewer();
	
	public TableItem[] getItems();
	
	public ISelection getSelection();
	
	public Widget getColumn(int index);
	
	public void addTableListener(int eventType, Listener listener);
	
	public void addSelectionChangedListener(ISelectionChangedListener listener);
	
	public void addDoubleClickListener(IDoubleClickListener listener);
	
	public void display(TablePageConfig<?> pageConfig);
	
	public int getStartNo();

	public DataTableIf getDataTable();

	public void setDataTable(DataTableIf dataTable);

	public void setDatas(Object... datas);

	public Display getDisplay();

}
