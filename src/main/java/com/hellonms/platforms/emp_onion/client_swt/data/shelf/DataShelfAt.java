package com.hellonms.platforms.emp_onion.client_swt.data.shelf;

import org.eclipse.swt.graphics.Point;

public abstract class DataShelfAt implements DataShelfIf {

	protected ModelClient4Shelf[][] shelveses = {};

	@Override
	public int getRowCount() {
		return shelveses.length;
	}

	@Override
	public int getColumnCount(int row) {
		return shelveses[row].length;
	}

	@Override
	public String getImagePath(int row, int column) {
		return shelveses[row][column].getImagePath();
	}

	@Override
	public Point getImageLocation(int row, int column) {
		return shelveses[row][column].getImageLocation();
	}
	
	@Override
	public String getText(int row, int column) {
		return shelveses[row][column].getText();
	}
	
	@Override
	public int getRotate(int row, int column) {
		return shelveses[row][column].getRotate();
	}

}
