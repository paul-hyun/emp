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

public class ButtonToggle extends Label {

	private Image imageNormal;

	@SuppressWarnings("unused")
	private Image imageOver;

	private Image imageDown;

	private Image imageDisabled;

	private Image drawImage;

	private boolean selection = false;

	private boolean enabled = true;

	private SelectionListener listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param imageNormal
	 *            일반 이미지
	 * @param imageOver
	 *            마우스 오버 시 이미지
	 * @param imageDown
	 *            마우스 버튼 다운 시 이미지
	 * @param imageDisabled
	 *            이미지 오류 시 이미지
	 */
	public ButtonToggle(Composite parent, int style, Image imageNormal, Image imageOver, Image imageDown, Image imageDisabled) {
		super(parent, style);

		this.imageNormal = imageNormal;
		this.imageOver = imageOver;
		this.imageDown = imageDown;
		this.imageDisabled = imageDisabled;
		this.drawImage = imageNormal;

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				ButtonToggle.this.paintControl(e);
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) {
				selection = !selection;
				drawImage = selection ? ButtonToggle.this.imageDown : ButtonToggle.this.imageNormal;
				redraw();
				if (enabled && listener != null) {
					listener.widgetSelected(null);
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				drawImage = ButtonToggle.this.imageDown;
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
				drawImage = selection ? ButtonToggle.this.imageDown : ButtonToggle.this.imageNormal;
				redraw();
			}

			@Override
			public void mouseEnter(MouseEvent e) {
				drawImage = selection ? ButtonToggle.this.imageDown : ButtonToggle.this.imageNormal;
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
	 * 선택 상태를 반환합니다.
	 * 
	 * @return 선택 상태
	 */
	public boolean isSelection() {
		return selection;
	}

	/**
	 * 선택 상태를 설정합니다.
	 * 
	 * @param selection
	 *            선택 상태
	 */
	public void setSelection(boolean selection) {
		this.selection = selection;
		drawImage = selection ? ButtonToggle.this.imageDown : ButtonToggle.this.imageNormal;
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
