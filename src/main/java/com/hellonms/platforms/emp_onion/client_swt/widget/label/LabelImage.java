package com.hellonms.platforms.emp_onion.client_swt.widget.label;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * LabelImage
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class LabelImage extends Label implements PaintListener {

	private Image image;

	private String text = "";

	private boolean blink;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public LabelImage(Composite parent, int style) {
		this(parent, style, null);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param image
	 *            이미지
	 */
	public LabelImage(Composite parent, int style, Image image) {
		super(parent, style);
		this.image = image;

		addPaintListener(this);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void paintControl(PaintEvent e) {
		if (image != null) {
			Rectangle rect = image.getBounds();
			if (!blink) {
				e.gc.drawImage(image, 0, 0);
			}

			e.gc.setFont(getFont());
			e.gc.setForeground(getForeground());
			Point size = e.gc.textExtent(text);
			e.gc.drawText(text, (rect.width - size.x) / 2, (rect.height - size.y) / 2, true);
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		if (image != null) {
			Rectangle rect = image.getBounds();
			return new Point(rect.width, rect.height);
		} else {
			return new Point(0, 0);
		}
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void setImage(Image image) {
		this.image = image;
		redraw();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String string) {
		this.text = string;
		redraw();
	}

	/**
	 * 깜빡임 상태를 반환합니다.
	 * 
	 * @return 깜빡임 상태
	 */
	public boolean isBlink() {
		return blink;
	}

	/**
	 * 깜빡임 상태를 설정합니다.
	 * 
	 * @param blink
	 *            깜빡임 상태
	 */
	public void setBlink(boolean blink) {
		this.blink = blink;
		redraw();
	}

}
