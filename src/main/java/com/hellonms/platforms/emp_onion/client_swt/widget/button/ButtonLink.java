package com.hellonms.platforms.emp_onion.client_swt.widget.button;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.CURSOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * ButtonLink
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ButtonLink extends CLabel {

	protected SelectionListener listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public ButtonLink(Composite parent, int style) {
		super(parent, style);

		createGUI();
	}

	protected void createGUI() {
		setMargins(0, 0, 0, 2);
		setForeground(ThemeFactory.getColor(COLOR_ONION.BUTTON_LINK_FG));
		setCursor(ThemeFactory.getCursor(CURSOR_ONION.HAND));
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle rect = getBounds();
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.BUTTON_LINK_FG));
				e.gc.drawLine(22, rect.height - 2, rect.width - 1, rect.height - 2);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (listener != null) {
					listener.widgetSelected(null);
				}
			}
		});
		setImage(ThemeFactory.getImage(IMAGE_ONION.BUTTON_LINK));
	}

	@Override
	protected void checkSubclass() {
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

}
