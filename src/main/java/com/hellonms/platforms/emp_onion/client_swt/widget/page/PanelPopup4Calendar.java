package com.hellonms.platforms.emp_onion.client_swt.widget.page;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_util.date.UtilDate;

public class PanelPopup4Calendar extends PanelPopup {

	public interface PanelPopup4CalendarListenerIf {

		public void selected(Date date);

	}
	
	public static final int WIDTH = 200;
	
	public static final int HEIGHT = 220;

	private Calendar calendar = Calendar.getInstance();

	private Point location;

	private PanelPopup4CalendarListenerIf listener;

	private Label labelThisMonth;

	private CLabel[] labelDate;

	public PanelPopup4Calendar(Shell parent, Date date, Point location, PanelPopup4CalendarListenerIf listener) {
		super(parent);

		calendar.setTime(date);

		this.location = location;
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setSize(200, 220);
		setLocation(location);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
				e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
			}
		});

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 2;
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.horizontalSpacing = 0;
		setLayout(gridLayout);

		Composite panelNavigation = new Composite(this, SWT.NONE);
		panelNavigation.setLayout(new GridLayout(5, false));
		panelNavigation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		panelNavigation.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		panelNavigation.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Label labelPrevYear = new Label(panelNavigation, SWT.NONE);
		labelPrevYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				calendar.add(Calendar.YEAR, -1);
				display();
			}
		});
		labelPrevYear.setBounds(0, 0, 61, 12);
		labelPrevYear.setText("◀◀");

		Label labelPrevMonth = new Label(panelNavigation, SWT.NONE);
		labelPrevMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				calendar.add(Calendar.MONTH, -1);
				display();
			}
		});
		labelPrevMonth.setText("◀");

		labelThisMonth = new Label(panelNavigation, SWT.NONE);
		labelThisMonth.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));

		Label labelNextMonth = new Label(panelNavigation, SWT.NONE);
		labelNextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				calendar.add(Calendar.MONTH, 1);
				display();
			}
		});
		labelNextMonth.setText("▶");

		Label labelNextYear = new Label(panelNavigation, SWT.NONE);
		labelNextYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				calendar.add(Calendar.YEAR, 1);
				display();
			}
		});
		labelNextYear.setText("▶▶");

		Composite panelCalendar = new Composite(this, SWT.NONE);
		GridLayout gl_panelCalendar = new GridLayout(7, true);
		gl_panelCalendar.verticalSpacing = 1;
		gl_panelCalendar.marginWidth = 5;
		gl_panelCalendar.marginHeight = 5;
		gl_panelCalendar.horizontalSpacing = 1;
		panelCalendar.setLayout(gl_panelCalendar);
		panelCalendar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		panelCalendar.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		panelCalendar.setBackgroundMode(SWT.INHERIT_DEFAULT);

		Label labelSun = new Label(panelCalendar, SWT.NONE);
		labelSun.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelSun.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.SUN));
		labelSun.setForeground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_WEEK_SUN));

		Label labelMon = new Label(panelCalendar, SWT.NONE);
		labelMon.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelMon.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.MON));

		Label labelTue = new Label(panelCalendar, SWT.NONE);
		labelTue.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelTue.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.TUE));

		Label labelWed = new Label(panelCalendar, SWT.NONE);
		labelWed.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelWed.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.WED));

		Label labelThu = new Label(panelCalendar, SWT.NONE);
		labelThu.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelThu.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.THU));

		Label labelFri = new Label(panelCalendar, SWT.NONE);
		labelFri.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelFri.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.FRI));

		Label labelSat = new Label(panelCalendar, SWT.NONE);
		labelSat.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		labelSat.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.SAT));
		labelSat.setForeground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_WEEK_SAT));

		labelDate = new CLabel[6 * 7];
		for (int i = 0; i < labelDate.length; i++) {
			labelDate[i] = new CLabel(panelCalendar, SWT.CENTER);
			labelDate[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			labelDate[i].addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					CLabel label = (CLabel) e.widget;
					try {
						Integer.parseInt(label.getText());
						label.setBackground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_SELECTED_BG));
					} catch (Throwable ex) {
						label.setData(null);
					}
				}

				@Override
				public void mouseExit(MouseEvent e) {
					CLabel label = (CLabel) e.widget;
					Color background = (Color) label.getData();
					if (background != null) {
						label.setBackground((Color) label.getData());
					}
				}
			});
			labelDate[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					if (e.button == 1) {
						CLabel label = (CLabel) e.widget;
						try {
							int date = Integer.parseInt(label.getText());
							calendar.set(Calendar.DAY_OF_MONTH, date);

							display();
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				}

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					if (e.button == 1) {
						CLabel label = (CLabel) e.widget;
						try {
							int date = Integer.parseInt(label.getText());
							calendar.set(Calendar.DAY_OF_MONTH, date);

							listener.selected(calendar.getTime());
							PanelPopup4Calendar.this.dispose();
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				}
			});
			if (i % 7 == 0) {
				labelDate[i].setForeground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_WEEK_SUN));
			} else if (i % 7 == 6) {
				labelDate[i].setForeground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_WEEK_SAT));
			}
		}

		Button buttonOk = new Button(this, SWT.NONE);
		buttonOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.selected(calendar.getTime());
				PanelPopup4Calendar.this.dispose();
			}
		});
		GridData gd_buttonOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_buttonOk.widthHint = 60;
		buttonOk.setLayoutData(gd_buttonOk);
		buttonOk.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.OK));

		Button buttonCancel = new Button(this, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PanelPopup4Calendar.this.dispose();
			}
		});
		GridData gd_buttonCancel = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_buttonCancel.widthHint = 60;
		buttonCancel.setLayoutData(gd_buttonCancel);
		buttonCancel.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.CANCEL));

		display();
	}

	private void display() {
		labelThisMonth.setText(UtilDate.format(UtilDate.MONTH_FORMAT, calendar.getTime()));

		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) % 7 - today;
		while (dayOfWeek < 0) {
			dayOfWeek += 7;
		}
		int dayOfMonth = 0;
		int lastDayOfMonth = getLastDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
		for (int i = 0; i < labelDate.length; i++) {
			if (i < dayOfWeek) {
				labelDate[i].setText("");
			} else if (dayOfMonth < lastDayOfMonth) {
				labelDate[i].setText(String.valueOf(++dayOfMonth));
			} else {
				++dayOfMonth;
				labelDate[i].setText("");
			}

			if (today == dayOfMonth) {
				labelDate[i].setBackground(ThemeFactory.getColor(COLOR_ONION.CALENDAR_SELECTED_BG));
				labelDate[i].setData(labelDate[i].getBackground());
			} else {
				labelDate[i].setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
				labelDate[i].setData(labelDate[i].getBackground());
			}
		}

		labelThisMonth.getParent().layout();
		labelDate[0].getParent().layout();
	}

	private int getLastDayOfMonth(int year, int month) {
		switch (month) {
		case 1:
			return 31;
		case 2: {
			if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) {
				return 29;
			} else {
				return 28;
			}
		}
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		}
		return 31;
	}

}
