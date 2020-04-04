package com.hellonms.platforms.emp_onion.client_swt.data.shelf;

import org.eclipse.swt.graphics.Point;

public class ModelClient4Shelf {

	private String imagePath;

	private Point imageLocation;
	
	private String text;
	
	private int rotate;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Point getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(Point imageLocation) {
		this.imageLocation = imageLocation;
	}

	public void setImageLocation(int x, int y) {
		if (imageLocation == null) {
			imageLocation = new Point(x, y);
		} else {
			imageLocation.x = x;
			imageLocation.y = y;
		}
	}

	public int getImageLocationX() {
		return imageLocation.x;
	}

	public int getImageLocationY() {
		return imageLocation.y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

}