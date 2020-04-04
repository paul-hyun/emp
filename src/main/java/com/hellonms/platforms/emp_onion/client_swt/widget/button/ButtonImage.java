package com.hellonms.platforms.emp_onion.client_swt.widget.button;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * <p>
 * ButtonImage
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ButtonImage extends Label {

	private Image imageNormal;

	private Image imageOver;

	private Image imageDown;

	private Image imageDisabled;

	private Image drawImage;

	private boolean enabled = true;

	private SelectionListener listener;

	/**
	 * 생성자입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param imageNormal
	 *            노멀 이미지
	 * @param imageOver
	 *            마우스 오버 시 이미지
	 * @param imageDown
	 *            마우스 버튼 다운 시 이미지
	 * @param imageDisabled
	 *            이미지 오류 시 이미지
	 */
	public ButtonImage(Composite parent, int style, Image imageNormal, Image imageOver, Image imageDown, Image imageDisabled) {
		super(parent, style);

		this.imageNormal = imageNormal;
		this.imageOver = imageOver;
		this.imageDown = imageDown;
		this.imageDisabled = imageDisabled;
		this.drawImage = imageNormal;

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				ButtonImage.this.paintControl(e);
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				drawImage = ButtonImage.this.imageNormal;
				redraw();
				if (enabled && listener != null) {
					listener.widgetSelected(null);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				drawImage = ButtonImage.this.imageDown;
				redraw();
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent e) {
			}

			@Override
			public void mouseExit(MouseEvent e) {
				drawImage = ButtonImage.this.imageNormal;
				redraw();
			}

			@Override
			public void mouseEnter(MouseEvent e) {
				drawImage = ButtonImage.this.imageOver;
				redraw();
			}
		});
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Rectangle rect = imageNormal.getBounds();
		return new Point(rect.width, rect.height);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		redraw();
	}

	/**
	 * 선택 리스너를 추가합니다.
	 * 
	 * @param listener
	 *            선택 리스너
	 */
	public void addSelectionListener(SelectionListener listener) {
		this.listener = listener;
	}

	private void paintControl(PaintEvent e) {
		Image image;
		if (enabled) {
			image = drawImage;
		} else {
			image = imageDisabled;
		}
		e.gc.drawImage(image, 0, 0);
	}

}
