package com.hellonms.platforms.emp_orange.client_swt.page.toolbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.client_swt.util.sound.UtilSound;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.menu.MenuStack;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.Page;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PageBlinkIf;
import com.hellonms.platforms.emp_onion.client_swt.widget.viewpart.PAGE;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.ApplicationProperty;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.ModelClient4EventListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.ModelClient4NetworkTreeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4Event;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.COLOR_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.FONT_ORANGE;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;
import com.hellonms.platforms.emp_util.queue.UtilQueue;

/**
 * <p>
 * Page4Toolbar
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Page4Toolbar extends Page implements ModelClient4NetworkTreeListenerIf, ModelClient4EventListenerIf, PageBlinkIf {

	/**
	 * 툴바 페이지의 리스너 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface Page4ToolbarListenerIf {

		public void selectMenu(PAGE page);

		/**
		 * 현재알람 화면을 오픈합니다.
		 * 
		 * @param severity
		 *            심각도
		 */
		public void openAlarmActive(SEVERITY severity);

	}

	/**
	 * 사운드 상태를 나타내는 열거형 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public enum SOUND_STATE {
		ON, PAUSE, OFF;
	}

	/**
	 * 사운드 스레드 클래스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public class SoundThread extends Thread {

		/**
		 * 생성자 입니다.
		 */
		public SoundThread() {
			super("Page4Toolbar::SoundThread");
		}

		@Override
		public void run() {
			while (!Page4Toolbar.this.isDisposed()) {
				try {
					SEVERITY severity = soundQueue.pop();
					switch (soundState) {
					case ON:
						playSound(severity);
						break;
					case PAUSE:
						soundState = SOUND_STATE.ON;
						Page4Toolbar.this.getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								try {
									labelSound.setImage(ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_SPEAKER_ON));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						playSound(severity);
						break;
					case OFF:
						break;
					}
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 사운드를 재생합니다.
		 * 
		 * @param severity
		 *            심각도
		 */
		public void playSound(SEVERITY severity) {
			UtilSound.playSound(UtilResource4Orange.getSound(severity));
		}

	}

	/**
	 * 제품 이미지
	 */
	protected Image imageProduct;

	/**
	 * 리스너
	 */
	protected Page4ToolbarListenerIf listener;

	/**
	 * 어플리케이션 속성
	 */
	protected ApplicationProperty applicationProperty;

	/**
	 * 통신오류 알람 이미지라벨
	 */
	protected LabelImage labelCf;

	/**
	 * 심각 알람 이미지라벨
	 */
	protected LabelImage labelCritical;

	/**
	 * 주의 알람 이미지라벨
	 */
	protected LabelImage labelMajor;

	/**
	 * 경계 알람 이미지라벨
	 */
	protected LabelImage labelMinor;

	/**
	 * 사운드 이미지라벨
	 */
	protected LabelImage labelSound;

	/**
	 * 알람 깜빡임 요약 플래그
	 */
	protected boolean blink_alarm_summary_flag = false;

	/**
	 * 사운드 큐
	 */
	protected UtilQueue<SEVERITY> soundQueue = new UtilQueue<SEVERITY>(32);

	/**
	 * 사운드 상태
	 */
	protected SOUND_STATE soundState = SOUND_STATE.ON;

	/**
	 * 사운드 마지막 재생 event_id
	 */
	protected long max_event_id = 0L;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param userSession
	 *            사용자 세션
	 * @param listener
	 *            리스너
	 * @param applicationProperty
	 *            어플리케이션 속성
	 */
	public Page4Toolbar(Composite parent, int style, ApplicationProperty applicationProperty, Page4ToolbarListenerIf listener) {
		super(parent, style, "", "");
		this.imageProduct = ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_CI);
		this.listener = listener;
		this.applicationProperty = applicationProperty;

		createGUI();
		display(true);

		new SoundThread().start();

		ModelClient4NetworkTree.getInstance().addModelClient4NetworkTreeListener(Page4Toolbar.this);
		ModelClient4NetworkTree.getInstance().addModelClient4EventListener(Page4Toolbar.this);
		ApplicationProperty.addPageBlink(Page4Toolbar.this);
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				ModelClient4NetworkTree.getInstance().removeModelClient4NetworkTreeListener(Page4Toolbar.this);
				ModelClient4NetworkTree.getInstance().removeModelClient4EventListener(Page4Toolbar.this);
				ApplicationProperty.removePageBlink(Page4Toolbar.this);
			}
		});
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setLayout(new FormLayout());
		setBackground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
		setBackgroundImage(ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_BG));
		setBackgroundMode(SWT.INHERIT_DEFAULT);

		Composite panelContents = new Composite(this, SWT.NONE);
		panelContents.setLayout(new FormLayout());
		FormData fd_panelContents = new FormData();
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -5);
		panelContents.setLayoutData(fd_panelContents);

		MenuStack menu = new MenuStack(panelContents, SWT.NONE);
		FormData fd_menu = new FormData();
		fd_menu.top = new FormAttachment(0, 5);
		fd_menu.left = new FormAttachment(0, 5);
		menu.setLayoutData(fd_menu);
		applicationProperty.createMenu(menu, listener);

		Control controlProductEnd = createProduct(panelContents);
		Control controlInsert1 = createInsert1(panelContents, controlProductEnd);
		Control controlAlarmEnd = createAlarm(panelContents, controlInsert1);
		@SuppressWarnings("unused")
		Control controlInsert2 = createInsert2(panelContents, controlAlarmEnd);
	}

	/**
	 * 제품 이미지라벨을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @return 이미지라벨
	 */
	protected Control createProduct(Composite parent) {
		LabelText labelProduct = new LabelText(parent, SWT.NONE);
		labelProduct.setImage(imageProduct);
		FormData fd_labelProduct = new FormData();
		fd_labelProduct.top = new FormAttachment(0, 0);
		fd_labelProduct.right = new FormAttachment(100);
		labelProduct.setLayoutData(fd_labelProduct);

		return labelProduct;
	}

	/**
	 * 솔루션에서 추가될 컨트롤의 예비영역을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param controlRight
	 *            컨트롤
	 * @return 컨트롤
	 */
	protected Control createInsert1(Composite parent, Control controlRight) {
		return controlRight;
	}

	/**
	 * 알람 컨트롤을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param controlRight
	 *            오른쪽 컨트롤
	 * @return 알람 컨트롤
	 */
	protected Control createAlarm(Composite parent, Control controlRight) {
		labelSound = new LabelImage(parent, SWT.CENTER, ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_SPEAKER_ON));
		labelSound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				switch (soundState) {
				case ON:
					soundState = SOUND_STATE.PAUSE;
					labelSound.setImage(ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_SPEAKER_PAUSE));
					break;
				case PAUSE:
					soundState = SOUND_STATE.OFF;
					labelSound.setImage(ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_SPEAKER_OFF));
					break;
				case OFF:
					soundState = SOUND_STATE.ON;
					labelSound.setImage(ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_SPEAKER_ON));
					break;
				}
				labelSound.redraw();
			}
		});
		labelSound.setSize(59, 66);
		FormData fd_labelSound = new FormData();
		fd_labelSound.left = new FormAttachment(controlRight, -labelSound.getImage().getBounds().width - 16, SWT.LEFT);
		fd_labelSound.right = new FormAttachment(controlRight, -16);
		fd_labelSound.bottom = new FormAttachment(controlRight, labelSound.getImage().getBounds().height);
		fd_labelSound.top = new FormAttachment(controlRight, 0, SWT.TOP);
		labelSound.setLayoutData(fd_labelSound);

		labelMinor = new LabelImage(parent, SWT.CENTER, ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_ALARM_MINOR));
		labelMinor.setToolTipText(SEVERITY.MINOR.name());
		labelMinor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				labelMinor.setBlink(false);
				labelMinor.setData("blink", false);
				listener.openAlarmActive(SEVERITY.MINOR);
			}
		});
		labelMinor.setSize(59, 66);
		labelMinor.setForeground(ThemeFactory.getColor(COLOR_ORANGE.TOOLBAR_ALARM_FG));
		labelMinor.setFont(ThemeFactory.getFont(FONT_ORANGE.TOOLBAR_ALARM));
		FormData fd_labelMinor = new FormData();
		fd_labelMinor.left = new FormAttachment(labelSound, -labelMinor.getImage().getBounds().width, SWT.LEFT);
		fd_labelMinor.right = new FormAttachment(labelSound, 0);
		fd_labelMinor.bottom = new FormAttachment(labelSound, labelMinor.getImage().getBounds().height);
		fd_labelMinor.top = new FormAttachment(labelSound, 0, SWT.TOP);
		labelMinor.setLayoutData(fd_labelMinor);
		labelMinor.setText("0");

		labelMajor = new LabelImage(parent, SWT.CENTER, ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_ALARM_MAJOR));
		labelMajor.setToolTipText(SEVERITY.MAJOR.name());
		labelMajor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				labelMajor.setBlink(false);
				labelMajor.setData("blink", false);
				listener.openAlarmActive(SEVERITY.MAJOR);
			}
		});
		labelMajor.setSize(59, 66);
		labelMajor.setForeground(ThemeFactory.getColor(COLOR_ORANGE.TOOLBAR_ALARM_FG));
		labelMajor.setFont(ThemeFactory.getFont(FONT_ORANGE.TOOLBAR_ALARM));
		FormData fd_labelMajor = new FormData();
		fd_labelMajor.left = new FormAttachment(labelMinor, -labelMajor.getImage().getBounds().width, SWT.LEFT);
		fd_labelMajor.right = new FormAttachment(labelMinor);
		fd_labelMajor.bottom = new FormAttachment(labelMinor, labelMajor.getImage().getBounds().height);
		fd_labelMajor.top = new FormAttachment(labelMinor, 0, SWT.TOP);
		labelMajor.setLayoutData(fd_labelMajor);
		labelMajor.setText("0");

		labelCritical = new LabelImage(parent, SWT.CENTER, ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_ALARM_CRITICAL));
		labelCritical.setToolTipText(SEVERITY.CRITICAL.name());
		labelCritical.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				labelCritical.setBlink(false);
				labelCritical.setData("blink", false);
				listener.openAlarmActive(SEVERITY.CRITICAL);
			}
		});
		labelCritical.setSize(59, 66);
		labelCritical.setForeground(ThemeFactory.getColor(COLOR_ORANGE.TOOLBAR_ALARM_FG));
		labelCritical.setFont(ThemeFactory.getFont(FONT_ORANGE.TOOLBAR_ALARM));
		FormData fd_labelCritical = new FormData();
		fd_labelCritical.left = new FormAttachment(labelMajor, -labelCritical.getImage().getBounds().width, SWT.LEFT);
		fd_labelCritical.right = new FormAttachment(labelMajor);
		fd_labelCritical.bottom = new FormAttachment(labelMajor, labelCritical.getImage().getBounds().height);
		fd_labelCritical.top = new FormAttachment(labelMajor, 0, SWT.TOP);
		labelCritical.setLayoutData(fd_labelCritical);
		labelCritical.setText("0");

		labelCf = new LabelImage(parent, SWT.CENTER, ThemeFactory.getImage(IMAGE_ORANGE.TOOLBAR_ALARM_CF));
		labelCf.setToolTipText(SEVERITY.COMMUNICATION_FAIL.name());
		labelCf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				labelCf.setBlink(false);
				labelCf.setData("blink", false);
				listener.openAlarmActive(SEVERITY.COMMUNICATION_FAIL);
			}
		});
		labelCf.setForeground(ThemeFactory.getColor(COLOR_ORANGE.TOOLBAR_ALARM_FG));
		labelCf.setFont(ThemeFactory.getFont(FONT_ORANGE.TOOLBAR_ALARM));
		FormData fd_labelCf = new FormData();
		fd_labelCf.left = new FormAttachment(labelCritical, -labelCf.getImage().getBounds().width, SWT.LEFT);
		fd_labelCf.right = new FormAttachment(labelCritical);
		fd_labelCf.bottom = new FormAttachment(labelCritical, labelCf.getImage().getBounds().height);
		fd_labelCf.top = new FormAttachment(labelCritical, 0, SWT.TOP);
		labelCf.setLayoutData(fd_labelCf);
		labelCf.setText("0");

		return labelCf;
	}

	/**
	 * 솔루션에서 추가될 컨트롤의 예비영역을 생성합니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param controlRight
	 *            컨트롤
	 * @return 컨트롤
	 */
	protected Control createInsert2(Composite parent, Control controlRight) {
		return controlRight;
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void display(final boolean progressBar) {
		try {
			final NODE root = ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
			final String stringCf = String.valueOf(root == null ? 0 : root.getCf());
			final String stringCritical = String.valueOf(root == null ? 0 : root.getCritical());
			final String stringMajor = String.valueOf(root == null ? 0 : root.getMajor());
			final String stringMinor = String.valueOf(root == null ? 0 : root.getMinor());

			if (!labelCf.isDisposed()) {
				if (!stringCf.equals(labelCf.getText())) {
					labelCf.setText(stringCf);
					labelCf.setData("blink", blink_alarm_summary_flag);
				}
			}
			if (!labelCritical.isDisposed() && !labelMajor.isDisposed() && !labelMinor.isDisposed()) {
				if (!stringCritical.equals(labelCritical.getText())) {
					labelCritical.setText(stringCritical);
					labelCritical.setData("blink", blink_alarm_summary_flag);
				}

				if (!stringMajor.equals(labelMajor.getText())) {
					labelMajor.setText(stringMajor);
					labelMajor.setData("blink", blink_alarm_summary_flag);
				}

				if (!stringMinor.equals(labelMinor.getText())) {
					labelMinor.setText(stringMinor);
					labelMinor.setData("blink", blink_alarm_summary_flag);
				}

				blink_alarm_summary_flag = true;
			}
		} catch (EmpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		if (!isDisposed()) {
			if (Thread.currentThread() == getDisplay().getThread()) {
				display(true);
			} else {
				getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						display(true);
					}
				});
			}
		}
	}

	@Override
	public void refresh(Model4Event[] model4Events) {
		playSound(model4Events);
	};

	@Override
	public void blink(final boolean blink) {
		if (!blink && soundState == SOUND_STATE.ON) {
			try {
				final NODE root = ModelClient4NetworkTree.getInstance().getNeGroup(Model4NeGroup.ROOT_NE_GROUP_ID);
				if (0 < root.getCritical_unack()) {
					soundQueue.push(SEVERITY.CRITICAL);
				} else if (0 < root.getMajor_unack()) {
					soundQueue.push(SEVERITY.MAJOR);
				} else if (0 < root.getMinor_unack()) {
					soundQueue.push(SEVERITY.MINOR);
				} else if (0 < root.getCf_unack()) {
					soundQueue.push(SEVERITY.COMMUNICATION_FAIL);
				}
			} catch (EmpException e) {
				e.printStackTrace();
			}
		}

		if (Thread.currentThread() == getDisplay().getThread()) {
			_blink(blink);
		} else {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					_blink(blink);
				}
			});
		}
	}

	/**
	 * 깜빡임 상태를 설정합니다.
	 * 
	 * @param blink
	 *            깜빡임 상태
	 */
	protected void _blink(boolean blink) {
		if (!labelCf.isDisposed()) {
			if (labelCf.getData("blink") != null && (Boolean) labelCf.getData("blink")) {
				labelCf.setBlink(blink);
			}
		}
		if (!labelCritical.isDisposed() && !labelMajor.isDisposed() && !labelMinor.isDisposed()) {
			if (labelCritical.getData("blink") != null && (Boolean) labelCritical.getData("blink")) {
				labelCritical.setBlink(blink);
			}

			if (labelMajor.getData("blink") != null && (Boolean) labelMajor.getData("blink")) {
				labelMajor.setBlink(blink);
			}

			if (labelMinor.getData("blink") != null && (Boolean) labelMinor.getData("blink")) {
				labelMinor.setBlink(blink);
			}
		}
	}

	/**
	 * 사운드를 재생합니다.
	 * 
	 * @param events
	 *            이벤트 모델 배열
	 * @return 사운드 재생여부
	 */
	public boolean playSound(Model4Event[] events) {
		if (soundState == SOUND_STATE.ON || soundState == SOUND_STATE.PAUSE) {
			boolean communication_fail = true;
			boolean critical = true;
			boolean major = true;
			boolean minor = true;
			boolean clear = true;
			boolean warning = true;
			boolean info = true;
			for (Model4Event event : events) {
				if (max_event_id < event.getEvent_id()) {
					max_event_id = Math.max(event.getEvent_id(), max_event_id);

					switch (event.getSeverity()) {
					case CRITICAL:
						if (critical) {
							soundQueue.push(event.getSeverity());
							critical = false;
						}
						break;
					case MAJOR:
						if (major) {
							soundQueue.push(event.getSeverity());
							major = false;
						}
						break;
					case MINOR:
						if (minor) {
							soundQueue.push(event.getSeverity());
							minor = false;
						}
						break;
					case CLEAR:
						if (clear) {
							soundQueue.push(event.getSeverity());
							clear = false;
						}
						break;
					case COMMUNICATION_FAIL:
						if (communication_fail) {
							soundQueue.push(event.getSeverity());
							communication_fail = false;
						}
						break;
					case WARNING:
						if (warning) {
							soundQueue.push(event.getSeverity());
							warning = false;
						}
						break;
					case INFO:
						if (info) {
							soundQueue.push(event.getSeverity());
							info = false;
						}
						break;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
