package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.FONT_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * Panel
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel extends Composite {

	private class MouseWheelListener implements Listener {
		@Override
		public void handleEvent(Event e) {
			Panel.this.getParent().notifyListeners(SWT.MouseWheel, e);
		}
	}

	private class MouseClickListener implements Listener {
		@Override
		public void handleEvent(Event e) {
			Panel.this.getParent().forceFocus();
		}
	}

	private MouseClickListener mouseClickListener = new MouseClickListener();

	private MouseWheelListener mouseWheelListener = new MouseWheelListener();

	private Composite contentComposite;

	private Composite panelToolbar;

	private String title;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param title
	 *            제목
	 */
	public Panel(Composite parent, int style, String title) {
		super(parent, style);
		this.title = title;

		createGUI();
	}

	private void createGUI() {
		setLayout(new FormLayout());
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
				e.gc.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
				e.gc.fillRoundRectangle(0, 0, Panel.this.getSize().x - 1, Panel.this.getSize().y - 1, 4, 4);

				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BORDER));
				e.gc.drawRectangle(4, 24, Panel.this.getSize().x - 9, Panel.this.getSize().y - 29);

				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.PANEL_FG));
				e.gc.setFont(ThemeFactory.getFont(FONT_ONION.PANEL_TITLE));
				e.gc.drawText(title, 10, 5);
			}
		});

		contentComposite = new Composite(this, SWT.NONE);
		contentComposite.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		contentComposite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		FormData fd_contentComposite = new FormData();
		fd_contentComposite.bottom = new FormAttachment(100, -5);
		fd_contentComposite.right = new FormAttachment(100, -5);
		fd_contentComposite.top = new FormAttachment(0, 25);
		fd_contentComposite.left = new FormAttachment(0, 5);
		contentComposite.setLayoutData(fd_contentComposite);

		panelToolbar = new Composite(this, SWT.NONE);
		panelToolbar.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		panelToolbar.setBackgroundMode(SWT.INHERIT_DEFAULT);
		RowLayout rl_panelToolbar = new RowLayout(SWT.HORIZONTAL);
		rl_panelToolbar.spacing = 10;
		rl_panelToolbar.marginBottom = 0;
		rl_panelToolbar.marginLeft = 0;
		rl_panelToolbar.marginRight = 0;
		rl_panelToolbar.marginTop = 0;
		panelToolbar.setLayout(rl_panelToolbar);
		FormData fd_panelToolbar = new FormData();
		fd_panelToolbar.right = new FormAttachment(contentComposite, 0, SWT.RIGHT);
		fd_panelToolbar.top = new FormAttachment(0, 5);
		panelToolbar.setLayoutData(fd_panelToolbar);

		addListener(SWT.MouseDown, mouseClickListener);
		addListener(SWT.MouseWheel, mouseWheelListener);
		contentComposite.addListener(SWT.MouseDown, mouseClickListener);
		contentComposite.addListener(SWT.MouseWheel, mouseWheelListener);
		panelToolbar.addListener(SWT.MouseDown, mouseClickListener);
		panelToolbar.addListener(SWT.MouseWheel, mouseWheelListener);
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 컨텐츠 컴포지트를 반환합니다.
	 * 
	 * @return 컨텐츠 컴포넌트
	 */
	protected Composite getContentComposite() {
		return contentComposite;
	}

	/**
	 * 판넬 툴바를 반환합니다.
	 * 
	 * @return 판넬 툴바
	 */
	protected Composite getPanelToolbar() {
		return panelToolbar;
	}

	/**
	 * 제목을 반환합니다.
	 * 
	 * @return 제목
	 */
	protected String getTitle() {
		return title;
	}

	/**
	 * 제목을 설정합니다.
	 * 
	 * @param title
	 *            제목
	 */
	protected void setTitle(String title) {
		this.title = title;
		this.redraw();
	}

}
