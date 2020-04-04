package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * PanelDetail
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelDetail extends ScrolledComposite {

	private class ValueMouseAdapter extends MouseAdapter {
		@Override
		public void mouseDown(MouseEvent e) {
			content.forceFocus();
		}
	}

	private Composite content;

	private List<Label> valueList = new ArrayList<Label>();

	private ValueMouseAdapter valueMouseAdapter = new ValueMouseAdapter();

	/**
	 * 생성자입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param style		스타일
	 * @param names		각 항목의 제목들
	 */
	public PanelDetail(Composite parent, int style, String... names) {
		super(parent, style | SWT.H_SCROLL | SWT.V_SCROLL);
		getHorizontalBar().setIncrement(8);
		getVerticalBar().setIncrement(8);

		content = new Composite(this, SWT.NONE);
		content.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		content.setBackgroundMode(SWT.INHERIT_DEFAULT);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		content.setLayout(gridLayout);

		this.setExpandHorizontal(true);
		this.setExpandVertical(true);
		this.setContent(content);

		for (String name : names) {
			Label labelName = new Label(content, SWT.NONE);
			labelName.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
			labelName.setText(name);
			labelName.addMouseListener(valueMouseAdapter);

			Label labelDot = new Label(content, SWT.NONE);
			labelDot.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
			labelDot.setText(" : ");
			labelDot.addMouseListener(valueMouseAdapter);

			Label labelValue = new Label(content, SWT.NONE);
			labelValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			labelValue.addMouseListener(valueMouseAdapter);

			valueList.add(labelValue);
		}
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 폰트를 설정합니다.
	 * 
	 * @param index	항목의 위치
	 * @param font	폰트
	 */
	public void setFont(int index, Font font) {
		if (-1 < index & index < valueList.size()) {
			valueList.get(index).setFont(font);
		}
	}

	/**
	 * 각 항목의 값을 설정합니다.
	 * 
	 * @param values	각 항목의 값들
	 */
	public void setText(Object... values) {
		for (int i = 0; i < valueList.size() && i < values.length; i++) {
			valueList.get(i).setText(String.valueOf(values[i]));
		}
		for (int i = values.length; i < valueList.size(); i++) {
			valueList.get(i).setText("");
		}

		content.pack();
		this.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}
