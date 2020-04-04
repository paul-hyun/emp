package com.hellonms.platforms.emp_orange.client_swt.page.security.login;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.util.resource.UtilResource;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * Dialog4Login
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Dialog4Login extends Shell {

	/**
	 * 로그인 페이지
	 */
	protected Page4Login page4Login;

	/**
	 * 배경 이미지
	 */
	protected Image bg;

	/**
	 * 생성자 입니다.
	 * 
	 * @param display
	 *            Display 객체
	 * @param bg
	 *            배경 이미지
	 */
	public Dialog4Login(Display display, Image bg) {
		super(display, SWT.NO_TRIM);
		this.bg = bg;

		createGUI();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setImages(new Image[] { ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_16), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_24), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_32), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_64), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_128), //
				ThemeFactory.getImage(IMAGE_ONION.APPLICATION_ICON_256), //
		});

		setText("Login");
		setSize(bg.getImageData().width, bg.getImageData().height);
		setBackground(UtilResource.getColor(239, 239, 239));
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		page4Login = new Page4Login(this, SWT.NONE, bg);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void open() {
		super.open();
		super.layout();

		Display display = Display.getCurrent();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * 로그인 페이지를 반환합니다.
	 * 
	 * @return 로그인 페이지
	 */
	public Page4Login getPage4Login() {
		return page4Login;
	}

}
