package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * PanelRound
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelRound extends Composite {

	private Composite contentComposite;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public PanelRound(Composite parent, int style) {
		super(parent, style);

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	private void createGUI() {
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		setLayout(fillLayout);
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_ROUND_BORDER));
				e.gc.drawRoundRectangle(0, 0, PanelRound.this.getSize().x - 1, PanelRound.this.getSize().y - 1, 4, 4);
			}
		});

		contentComposite = new Composite(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 컨텐츠 컴포지트를 반환합니다.
	 * 
	 * @return 컨텐츠 컴포지트
	 */
	public Composite getContentComposite() {
		return contentComposite;
	}
}
