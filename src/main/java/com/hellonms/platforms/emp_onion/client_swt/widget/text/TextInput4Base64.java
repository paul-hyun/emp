package com.hellonms.platforms.emp_onion.client_swt.widget.text;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_onion.client_swt.widget.button.ButtonClick;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * TextInput4Base64
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class TextInput4Base64 extends Composite implements ControlInputIf {

	private TextInput4String textValue;

	private long min_value;

	private long max_value;

	private String guide;

	private PanelInputListenerIf listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 */
	public TextInput4Base64(Composite parent, int style) {
		this(parent, style, Integer.MIN_VALUE, Integer.MAX_VALUE, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param guide
	 *            가이드 (설명)
	 */
	public TextInput4Base64(Composite parent, int style, String guide) {
		this(parent, style, Integer.MIN_VALUE, Integer.MAX_VALUE, guide);
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param min_value
	 *            문자열의 최소 길이
	 * @param max_value
	 *            문자열의 최대 길이
	 */
	public TextInput4Base64(Composite parent, int style, long min_value, long max_value) {
		this(parent, style, min_value, max_value, "");
	}

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param min_value
	 *            문자열의 최소 길이
	 * @param max_value
	 *            문자열의 최대 길이
	 * @param guide
	 *            가이드 (설명)
	 */
	public TextInput4Base64(Composite parent, int style, long min_value, long max_value, String guide) {
		super(parent, style);
		this.min_value = min_value;
		this.max_value = max_value;
		this.guide = guide;

		createGUI();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		textValue = new TextInput4String(this, SWT.BORDER | SWT.READ_ONLY, min_value, max_value, guide);
		textValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		textValue.setBackground(ThemeFactory.getColor(COLOR_ONION.READ_ONLY));

		ButtonClick buttonPopup = new ButtonClick(this, SWT.ARROW | SWT.DOWN);
		buttonPopup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		buttonPopup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TextInput4Base64.this.widgetSelected(e);
			}
		});
	}

	private void widgetSelected(SelectionEvent e) {
		if (listener != null) {
			listener.completeChanged();
		}
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		return textValue.isComplete();
	}

	@Override
	public String getValue() {
		return textValue.getValue();
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.listener = listener;
	}

}
