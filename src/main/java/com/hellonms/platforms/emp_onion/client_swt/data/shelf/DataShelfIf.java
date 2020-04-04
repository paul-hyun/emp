package com.hellonms.platforms.emp_onion.client_swt.data.shelf;

import org.eclipse.swt.graphics.Point;

public interface DataShelfIf {

	public void setDatas(Object... datas);

	public Object getData();

	public String getBackgroundImage();

	public int getRowCount();

	public int getColumnCount(int row);

	public String getImagePath(int row, int column);

	public Point getImageLocation(int row, int column);
	
	public String getText(int row, int column);
	
	public int getRotate(int row, int column);

}
