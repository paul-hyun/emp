package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * SelectorPath
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorPath extends Composite {

	/**
	 * 경로 선택 리스너의 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface SelectorPathListenerIf {

		/**
		 * 항목을 선택했을 때 호출됩니다.
		 * 
		 * @param value
		 *            선택한 항목
		 */
		public void selected(Object value);

	}

	private class PathItem {

		private Label labelHeader;

		private Label labelText;

		private Object value;

		public PathItem() {
			labelHeader = new Label(SelectorPath.this, SWT.NONE);
			labelHeader.setText("▶");

			labelText = new Label(SelectorPath.this, SWT.NONE);
			labelText.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					listener.selected(value);
				}
			});
			labelText.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					labelText.setBackground(ThemeFactory.getColor(COLOR_ONION.READ_WRITE));
				}

				@Override
				public void mouseExit(MouseEvent e) {
					labelText.setBackground(ThemeFactory.getColor(COLOR_ONION.READ_ONLY));
				}
			});
		}

		public void setValue(Object value) {
			this.value = value;
			labelText.setText(value.toString());
		}

		public void setVisible(boolean visible) {
			labelHeader.setVisible(visible);
			labelText.setVisible(visible);
		}

	}

	private SelectorPathListenerIf listener;

	private List<PathItem> pathItemList = new ArrayList<PathItem>();

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            경로 선택 리스너
	 */
	public SelectorPath(Composite parent, int style, SelectorPathListenerIf listener) {
		super(parent, style);
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		RowLayout rowLayout = new RowLayout(SWT.HORIZONTAL);
		rowLayout.center = true;
		setLayout(rowLayout);
		setBackground(ThemeFactory.getColor(COLOR_ONION.READ_ONLY));
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 경로를 설정합니다.
	 * 
	 * @param path
	 *            경로
	 */
	public void setPath(Object[] path) {
		while (pathItemList.size() < path.length) {
			pathItemList.add(new PathItem());
		}

		for (int i = 0; i < path.length; i++) {
			PathItem pathItem = pathItemList.get(i);
			pathItem.setValue(path[i]);
			pathItem.setVisible(true);
		}

		for (int i = path.length; i < pathItemList.size(); i++) {
			PathItem pathItem = pathItemList.get(i);
			pathItem.setVisible(false);
		}

		layout();
	}

}
