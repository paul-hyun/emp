package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;

import com.hellonms.platforms.emp_onion.client_swt.data.tree.DataTreeIf;

public interface PanelTreeIf {

	public void setBackground(Color color);

	public void setForeground(Color color);

	public void dispose();

	public void refresh();

	public ISelection getSelection();

	public void setSelection(ISelection selection);

	public boolean isFireSelectionChanged();
	
	public void setFireSelectionChanged(boolean fireSelectionChanged);
	
	public Tree getTree();

	public DataTreeIf getDataTree();
	
	public void setDataTree(DataTreeIf dataTree);

	public void setDatas(Object... datas);
	
	public void expandToLevel(int level);

	public Display getDisplay();

}
