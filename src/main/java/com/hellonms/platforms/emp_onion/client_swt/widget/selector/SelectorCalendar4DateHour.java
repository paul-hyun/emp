package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup4Calendar;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup4Calendar.PanelPopup4CalendarListenerIf;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

public class SelectorCalendar4DateHour extends Composite implements ControlInputIf {

	private class ChildListener implements PanelPopup4CalendarListenerIf {

		@Override
		public void selected(Date date) {
			SelectorCalendar4DateHour.this.select(date);
		}

	}

	private int duration_hour;

	private Text textDate;

	private Combo comboHour;

	private Date date = new Date();

	private String dateFormat = UtilDate.DATE_FORMAT;

	private SelectionListener selectionListener;

	private PanelInputListenerIf panelInputListener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param duration_hour
	 *            시간 간격
	 * @param duration_minute
	 *            분 간격
	 * @param duration_second
	 *            초 간격
	 */
	public SelectorCalendar4DateHour(Composite parent, int style, int duration_hour) {
		super(parent, style);

		this.duration_hour = duration_hour;

		createGUI();
		display();
		pack();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(9, false);
		gridLayout.verticalSpacing = 0;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);

		Button buttonPrev = new Button(this, SWT.ARROW | SWT.LEFT);
		buttonPrev.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		buttonPrev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				calendar.add(Calendar.DAY_OF_MONTH, -1);

				select(calendar.getTime());
			}
		});

		textDate = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		textDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new PanelPopup4Calendar(SelectorCalendar4DateHour.this.getShell(), date, getPanelPopupLocation(), new ChildListener()).open();
			}
		});
		textDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		textDate.setBackground(ThemeFactory.getColor(COLOR_ONION.WIDGET_READ_ONLY));

		Button buttonNext = new Button(this, SWT.ARROW | SWT.RIGHT);
		buttonNext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		buttonNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				calendar.add(Calendar.DAY_OF_MONTH, 1);

				select(calendar.getTime());
			}
		});

		Label sapceHour = new Label(this, SWT.NONE);
		sapceHour.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		sapceHour.setText(" ");

		comboHour = new Combo(this, SWT.READ_ONLY);
		for (int i = 0; i < 24; i += duration_hour) {
			comboHour.add(UtilString.formatZero(i, 2));
		}
		comboHour.select(0);
		comboHour.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorCalendar4DateHour.this.widgetSelected(e);
			}
		});
		comboHour.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	}

	private void widgetSelected(SelectionEvent e) {
		if (selectionListener != null) {
			selectionListener.widgetSelected(e);
		}
		if (panelInputListener != null) {
			panelInputListener.completeChanged();
		}
	}

	private void display() {
		textDate.setText(UtilDate.format(dateFormat, date));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int diff_hour = calendar.get(Calendar.HOUR_OF_DAY) % duration_hour;
		calendar.add(Calendar.HOUR_OF_DAY, diff_hour == 0 ? 0 : duration_hour - diff_hour);

		comboHour.setText(UtilString.formatZero(calendar.get(Calendar.HOUR_OF_DAY), 2));
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 시작시간을 반환합니다.
	 * 
	 * @return 시작시간
	 */
	public Date getStartHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(comboHour.getText()));
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 종료시간을 반환합니다.
	 * 
	 * @return 종료시간
	 */
	public Date getEndHour() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(comboHour.getText()));
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar.getTime();
	}

	/**
	 * 날짜를 설정합니다.
	 * 
	 * @param date
	 *            날짜
	 */
	public void setDate(Date date) {
		select(date);
	}

	/**
	 * 선택 리스너를 추가합니다.
	 * 
	 * @param listener
	 *            선택 리스너
	 */
	public void addSelectionListener(SelectionListener listener) {
		this.selectionListener = listener;
	}

	private void select(Date date) {
		this.date = date;
		display();

		SelectorCalendar4DateHour.this.widgetSelected(null);
	}

	@Override
	public Control getControl() {
		return this;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public Object getValue() {
		return getStartHour();
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.panelInputListener = listener;
	}

	protected Point getPanelPopupLocation() {
		return toDisplay(getSize().x - PanelPopup4Calendar.WIDTH, getSize().y + 1);
	}

}
