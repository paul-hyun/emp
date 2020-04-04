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

public class SelectorCalendar4DateTime extends Composite implements ControlInputIf {

	private class ChildListener implements PanelPopup4CalendarListenerIf {

		@Override
		public void selected(Date date) {
			SelectorCalendar4DateTime.this.select(date);
		}

	}

	private int duration_hour;

	private int duration_minute;

	private int duration_second;

	private boolean isHorizontal;

	private Text textDate;

	private Combo comboHour;

	private Combo comboMinute;

	private Combo comboSecond;

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
	public SelectorCalendar4DateTime(Composite parent, int style, int duration_hour, int duration_minute, int duration_second) {
		this(parent, style, duration_hour, duration_minute, duration_second, true);
	}

	public SelectorCalendar4DateTime(Composite parent, int style, int duration_hour, int duration_minute, int duration_second, boolean isHorizontal) {
		super(parent, style);

		this.duration_hour = duration_hour;
		this.duration_minute = duration_minute;
		this.duration_second = duration_second;
		this.isHorizontal = isHorizontal;

		createGUI();
		display();
		pack();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(isHorizontal ? 3 : 1, false);
		gridLayout.verticalSpacing = 2;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);

		Composite composite_date = new Composite(this, SWT.NONE);
		composite_date.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));

		GridLayout gl_date = new GridLayout(3, false);
		gl_date.verticalSpacing = 0;
		gl_date.marginWidth = 0;
		gl_date.marginHeight = 0;
		gl_date.horizontalSpacing = 0;
		composite_date.setLayout(gl_date);

		Button buttonPrev = new Button(composite_date, SWT.ARROW | SWT.LEFT);
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

		textDate = new Text(composite_date, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		textDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new PanelPopup4Calendar(SelectorCalendar4DateTime.this.getShell(), date, getPanelPopupLocation(), new ChildListener()).open();
			}
		});
		textDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));
		textDate.setBackground(ThemeFactory.getColor(COLOR_ONION.WIDGET_READ_ONLY));

		Button buttonNext = new Button(composite_date, SWT.ARROW | SWT.RIGHT);
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

		if (isHorizontal) {
			Label sapceHour = new Label(this, SWT.NONE);
			sapceHour.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			sapceHour.setText(" ");
		}

		Composite composite_Time = new Composite(this, SWT.NONE);
		composite_Time.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));

		GridLayout gl_time = new GridLayout(5, false);
		gl_time.verticalSpacing = 0;
		gl_time.marginWidth = 0;
		gl_time.marginHeight = 0;
		gl_time.horizontalSpacing = 0;
		composite_Time.setLayout(gl_time);

		comboHour = new Combo(composite_Time, SWT.READ_ONLY);
		for (int i = 0; i < 24; i += duration_hour) {
			comboHour.add(UtilString.formatZero(i, 2));
		}
		comboHour.select(0);
		comboHour.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorCalendar4DateTime.this.widgetSelected(e);
			}
		});
		comboHour.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));

		Label spaceMinute = new Label(composite_Time, SWT.NONE);
		spaceMinute.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		spaceMinute.setText(":");

		comboMinute = new Combo(composite_Time, SWT.READ_ONLY);
		for (int i = 0; i < 60; i += duration_minute) {
			comboMinute.add(UtilString.formatZero(i, 2));
		}
		comboMinute.select(0);
		comboMinute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorCalendar4DateTime.this.widgetSelected(e);
			}
		});
		comboMinute.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));

		Label spaceSecond = new Label(composite_Time, SWT.NONE);
		spaceSecond.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		spaceSecond.setText(":");

		comboSecond = new Combo(composite_Time, SWT.READ_ONLY);
		for (int i = 0; i < 60; i += duration_second) {
			comboSecond.add(UtilString.formatZero(i, 2));
		}
		comboSecond.select(0);
		comboSecond.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectorCalendar4DateTime.this.widgetSelected(e);
			}
		});
		comboSecond.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, !isHorizontal, false, 1, 1));
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

		int diff_second = calendar.get(Calendar.SECOND) % duration_second;
		calendar.add(Calendar.SECOND, diff_second == 0 ? 0 : duration_second - diff_second);
		int diff_minute = calendar.get(Calendar.MINUTE) % duration_minute;
		calendar.add(Calendar.MINUTE, diff_minute == 0 ? 0 : duration_minute - diff_minute);
		int diff_hour = calendar.get(Calendar.HOUR_OF_DAY) % duration_hour;
		calendar.add(Calendar.HOUR_OF_DAY, diff_hour == 0 ? 0 : duration_hour - diff_hour);

		comboHour.setText(UtilString.formatZero(calendar.get(Calendar.HOUR_OF_DAY), 2));
		comboMinute.setText(UtilString.formatZero(calendar.get(Calendar.MINUTE), 2));
		comboSecond.setText(UtilString.formatZero(calendar.get(Calendar.SECOND), 2));
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 시간을 반환합니다.
	 * 
	 * @return 시간
	 */
	public Date getTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(comboHour.getText()));
		calendar.set(Calendar.MINUTE, Integer.parseInt(comboMinute.getText()));
		calendar.set(Calendar.SECOND, Integer.parseInt(comboSecond.getText()));
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 날짜를 반환합니다.
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

		SelectorCalendar4DateTime.this.widgetSelected(null);
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
		return getTime();
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
