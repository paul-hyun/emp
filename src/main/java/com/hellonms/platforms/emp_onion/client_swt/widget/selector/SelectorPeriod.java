package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_util.date.UtilDate;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * SelectorPeriod
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorPeriod extends Composite {

	private Combo comboDate;

	private Combo comboHour;

	private Combo comboMinute;

	private Combo comboSecond;

	private long max_duration;

	private long duration_unit;

	private SelectionListener listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param max_duration
	 *            최대 기간
	 * @param duration_unit
	 *            기간 단위
	 */
	public SelectorPeriod(Composite parent, int style, long max_duration, long duration_unit) {
		super(parent, style);
		this.max_duration = max_duration;
		this.duration_unit = duration_unit;

		createGUI();
	}

	private void createGUI() {
		setLayout(new FormLayout());

		comboDate = new Combo(this, SWT.READ_ONLY);
		FormData fd_comboDate = new FormData();
		fd_comboDate.top = new FormAttachment(0);
		fd_comboDate.left = new FormAttachment(0);
		comboDate.setLayoutData(fd_comboDate);
		int date_unit = (int) ((UtilDate.DAY < duration_unit) ? duration_unit / UtilDate.DAY : 1);
		int date_index = 0;
		for (long duration = 0L; duration <= max_duration; duration += UtilDate.DAY * date_unit) {
			comboDate.add(UtilString.formatZero(date_index, 2));
			date_index += date_unit;
		}
		comboDate.select(0);
		comboDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (listener != null) {
					listener.widgetSelected(e);
				}
			}
		});

		Label labelDate = new Label(this, SWT.NONE);
		FormData fd_labelDate = new FormData();
		fd_labelDate.top = new FormAttachment(comboDate, 4, SWT.TOP);
		fd_labelDate.left = new FormAttachment(comboDate);
		labelDate.setLayoutData(fd_labelDate);
		labelDate.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.DAY));

		if (duration_unit < UtilDate.DAY) {
			comboHour = new Combo(this, SWT.READ_ONLY);
			FormData fd_comboHour = new FormData();
			fd_comboHour.left = new FormAttachment(labelDate, 10);
			fd_comboHour.top = new FormAttachment(comboDate, 0, SWT.TOP);
			comboHour.setLayoutData(fd_comboHour);
			int hour_unit = (int) ((UtilDate.HOUR < duration_unit) ? duration_unit / UtilDate.HOUR : 1);
			int hour_index = 0;
			for (long duration = 0L; duration < UtilDate.DAY; duration += UtilDate.HOUR * hour_unit) {
				comboHour.add(UtilString.formatZero(hour_index, 2));
				hour_index += hour_unit;
			}
			comboHour.select(0);
			comboHour.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (listener != null) {
						listener.widgetSelected(e);
					}
				}
			});

			Label labelHour = new Label(this, SWT.NONE);
			FormData fd_labelHour = new FormData();
			fd_labelHour.top = new FormAttachment(labelDate, 0, SWT.TOP);
			fd_labelHour.left = new FormAttachment(comboHour);
			labelHour.setLayoutData(fd_labelHour);
			labelHour.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.HOUR));

			if (duration_unit < UtilDate.HOUR) {
				comboMinute = new Combo(this, SWT.READ_ONLY);
				FormData fd_comboMinute = new FormData();
				fd_comboMinute.left = new FormAttachment(labelHour, 10);
				fd_comboMinute.top = new FormAttachment(comboHour, 0, SWT.TOP);
				comboMinute.setLayoutData(fd_comboMinute);
				int minute_unit = (int) ((UtilDate.MINUTE < duration_unit) ? duration_unit / UtilDate.MINUTE : 1);
				int minute_index = 0;
				for (long duration = 0L; duration < UtilDate.HOUR; duration += UtilDate.MINUTE * minute_unit) {
					comboMinute.add(UtilString.formatZero(minute_index, 2));
					minute_index += minute_unit;
				}
				comboMinute.select(0);
				comboMinute.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (listener != null) {
							listener.widgetSelected(e);
						}
					}
				});

				Label labelMinute = new Label(this, SWT.NONE);
				FormData fd_labelMinute = new FormData();
				fd_labelMinute.top = new FormAttachment(labelHour, 0, SWT.TOP);
				fd_labelMinute.left = new FormAttachment(comboMinute);
				labelMinute.setLayoutData(fd_labelMinute);
				labelMinute.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.MINUTE));

				if (duration_unit < UtilDate.MINUTE) {
					comboSecond = new Combo(this, SWT.READ_ONLY);
					FormData fd_comboSecond = new FormData();
					fd_comboSecond.left = new FormAttachment(labelMinute, 10);
					fd_comboSecond.top = new FormAttachment(comboMinute, 0, SWT.TOP);
					comboSecond.setLayoutData(fd_comboSecond);
					int second_unit = (int) ((UtilDate.SECOND < duration_unit) ? duration_unit / UtilDate.SECOND : 1);
					int second_index = 0;
					for (long duration = 0L; duration < UtilDate.MINUTE; duration += UtilDate.SECOND * second_unit) {
						comboSecond.add(UtilString.formatZero(second_index, 2));
						second_index += second_unit;
					}
					comboSecond.select(0);
					comboSecond.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (listener != null) {
								listener.widgetSelected(e);
							}
						}
					});

					Label labelSeconds = new Label(this, SWT.NONE);
					FormData fd_labelSeconds = new FormData();
					fd_labelSeconds.top = new FormAttachment(labelMinute, 0, SWT.TOP);
					fd_labelSeconds.left = new FormAttachment(comboSecond);
					labelSeconds.setLayoutData(fd_labelSeconds);
					labelSeconds.setText(UtilLanguage.getMessage(MESSAGE_CODE_ONION.SECOND));
				}
			}
		}
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

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		if (comboDate != null) {
			comboDate.setEnabled(enabled);
		}
		if (comboHour != null) {
			comboHour.setEnabled(enabled);
		}
		if (comboMinute != null) {
			comboMinute.setEnabled(enabled);
		}
		if (comboSecond != null) {
			comboSecond.setEnabled(enabled);
		}
	}

	/**
	 * 기간을 반환합니다.
	 * 
	 * @return 기간
	 */
	public long getPeriod() {
		long period = 0;
		if (comboDate != null) {
			period += UtilDate.DAY * Integer.parseInt(comboDate.getText());
		}
		if (comboHour != null) {
			period += UtilDate.HOUR * Integer.parseInt(comboHour.getText());
		}
		if (comboMinute != null) {
			period += UtilDate.MINUTE * Integer.parseInt(comboMinute.getText());
		}
		if (comboSecond != null) {
			period += UtilDate.SECOND * Integer.parseInt(comboSecond.getText());
		}
		return period;
	}

	/**
	 * 기간을 설정합니다.
	 * 
	 * @param period
	 *            기간
	 */
	public void setPeriod(long period) {
		int date = (int) (period / UtilDate.DAY);
		period %= UtilDate.DAY;
		int hour = (int) (period / UtilDate.HOUR);
		period %= UtilDate.HOUR;
		int minute = (int) (period / UtilDate.MINUTE);
		period %= UtilDate.MINUTE;
		int second = (int) (period / UtilDate.SECOND);
		period %= UtilDate.SECOND;

		if (comboDate != null) {
			comboDate.setText(UtilString.formatZero(date, 2));
		}
		if (comboHour != null) {
			comboHour.setText(UtilString.formatZero(hour, 2));
		}
		if (comboMinute != null) {
			comboMinute.setText(UtilString.formatZero(minute, 2));
		}
		if (comboSecond != null) {
			comboSecond.setText(UtilString.formatZero(second, 2));
		}
	}

	/**
	 * 기간을 문자열로 변환합니다.
	 * 
	 * @param period
	 *            기간
	 * @return 문자열
	 */
	public static String toPeriodString(long period) {
		StringBuilder stringBuilder = new StringBuilder();
		if (0 < period) {
			int date = (int) (period / UtilDate.DAY);
			if (0 < date) {
				stringBuilder.append(stringBuilder.length() == 0 ? "" : ", ").append(date).append("일");
			}
			period %= UtilDate.DAY;
		}
		if (0 < period) {
			int hour = (int) (period / UtilDate.HOUR);
			if (0 < hour) {
				stringBuilder.append(stringBuilder.length() == 0 ? "" : ", ").append(hour).append("시");
			}
			period %= UtilDate.HOUR;
		}
		if (0 < period) {
			int minute = (int) (period / UtilDate.MINUTE);
			if (0 < minute) {
				stringBuilder.append(stringBuilder.length() == 0 ? "" : ", ").append(minute).append("분");
			}
			period %= UtilDate.MINUTE;
		}
		if (0 < period) {
			int second = (int) (period / UtilDate.SECOND);
			if (0 < second) {
				stringBuilder.append(stringBuilder.length() == 0 ? "" : ", ").append(second).append("초");
			}
			period %= UtilDate.SECOND;
		}
		return stringBuilder.toString();
	}

}
