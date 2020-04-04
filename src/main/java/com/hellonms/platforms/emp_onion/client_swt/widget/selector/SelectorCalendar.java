package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.control.ControlInputIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelInputAt.PanelInputListenerIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_util.date.UtilDate;

/**
 * <p>
 * SelectorCalendar
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorCalendar extends Composite implements ControlInputIf {

	/**
	 * 일, 월, 년의 기간을 구분하는 enum 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public enum PERIOD {
		DATE, MONTH, YEAR
	}

	private class DateSelectorPopup extends PanelPopup {

		private Label labelThisMonth;

		private CLabel[] labelDate;

		private Calendar calendar = Calendar.getInstance();

		private DateSelectorPopup(Date date) {
			super(SelectorCalendar.this.getShell());
			calendar.setTime(date);

			createGUI();
		}

		private void createGUI() {
			setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));
			setBackgroundMode(SWT.INHERIT_DEFAULT);
			setSize(200, 220);
			Point location = SelectorCalendar.this.toDisplay(SelectorCalendar.this.getSize().x - getSize().x, SelectorCalendar.this.getSize().y + 1);
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

								select(calendar.getTime());
								DateSelectorPopup.this.dispose();
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
					select(calendar.getTime());
					DateSelectorPopup.this.dispose();
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
					DateSelectorPopup.this.dispose();
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

	private Text textDate;

	private Date date = new Date();

	private PERIOD period;

	private SelectionListener selectionListener;

	private PanelInputListenerIf inputPanelListener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param period
	 *            기간
	 */
	public SelectorCalendar(Composite parent, int style, PERIOD period) {
		super(parent, style);
		this.period = period;

		createGUI();
		display();
		pack();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(3, false);
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

				switch (period) {
				case DATE:
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					break;
				case MONTH:
					calendar.add(Calendar.MONTH, -1);
					break;
				case YEAR:
					calendar.add(Calendar.YEAR, -1);
					break;
				default:
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					break;
				}

				select(calendar.getTime());
			}
		});

		textDate = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		textDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				new DateSelectorPopup(date).open();
			}
		});
		textDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textDate.setBackground(ThemeFactory.getColor(COLOR_ONION.READ_ONLY));

		Button buttonNext = new Button(this, SWT.ARROW | SWT.RIGHT);
		buttonNext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		buttonNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);

				switch (period) {
				case DATE:
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					break;
				case MONTH:
					calendar.add(Calendar.MONTH, 1);
					break;
				case YEAR:
					calendar.add(Calendar.YEAR, 1);
					break;
				default:
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					break;
				}

				select(calendar.getTime());
			}
		});
	}

	private void widgetSelected(SelectionEvent e) {
		if (selectionListener != null) {
			selectionListener.widgetSelected(e);
		}
		if (inputPanelListener != null) {
			inputPanelListener.completeChanged();
		}
	}

	private void display() {
		textDate.setText(format(period, date));
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 시작날짜를 반환합니다.
	 * 
	 * @return 시작날짜
	 */
	public Date getStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		switch (period) {
		case DATE:
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			break;
		case MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			break;
		case YEAR:
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			break;
		default:
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			break;
		}

		return calendar.getTime();
	}

	/**
	 * 종료날짜를 반환합니다.
	 * 
	 * @return 종료날짜
	 */
	public Date getEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		switch (period) {
		case DATE:
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			break;
		case MONTH:
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			break;
		case YEAR:
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			break;
		default:
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			break;
		}

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
	 * 기간을 설정합니다.
	 * 
	 * @param period
	 *            기간(일, 월, 년)
	 */
	public void setPeriod(PERIOD period) {
		this.period = period;
		display();
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

		SelectorCalendar.this.widgetSelected(null);
	}

	/**
	 * 기간에 따른 날짜 형식을 반환합니다.
	 * 
	 * @param period
	 *            기간
	 * @param date
	 *            날짜
	 * @return 날짜 형식
	 */
	public String format(PERIOD period, Date date) {
		switch (period) {
		case DATE:
			return UtilDate.format(UtilDate.DATE_FORMAT, date);
		case MONTH:
			return UtilDate.format(UtilDate.MONTH_FORMAT, date);
		case YEAR:
			return UtilDate.format(UtilDate.YEAR_FORMAT, date);
		default:
			return UtilDate.format(UtilDate.DATE_FORMAT, date);
		}
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
		return getStartDate();
	}

	@Override
	public void setValue(Object value) {
	}

	@Override
	public void setPanelInputListener(PanelInputListenerIf listener) {
		this.inputPanelListener = listener;
	}

}
