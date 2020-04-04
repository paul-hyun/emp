package com.hellonms.platforms.emp_onion.client_swt.widget.dialog;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE;
import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * DialogProgress
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DialogProgress extends ProgressMonitorDialog {

	/**
	 * 작업 진행 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface ProgressTaskIf {

		/**
		 * 프로그래스바가 도는 동안 진행해야 할 작업을 구현합니다.
		 * 
		 * @return 작업 실행 결과
		 * @throws EmpException
		 */
		public Object run() throws EmpException;

	}

	private static class ObjectWrapper {
		private Object object;
	}

	private static final int WIDTH = 550;

	private static final int HEIGHT = 108;

	private static IProgressMonitor monitor;

	/**
	 * 작업 실행 메소드 입니다.
	 * 
	 * @param shell
	 *            부모 쉘
	 * @param progressBar
	 *            프로그래스 바 표시상태
	 * @param task
	 *            작업 진행 내용
	 * @return 작업 실행 결과
	 * @throws EmpException
	 */
	public static Object run(Shell shell, boolean progressBar, final ProgressTaskIf task) throws EmpException {
		try {
			final ObjectWrapper objectWrapper = new ObjectWrapper();

			if (progressBar) {
				final DialogProgress progress = new DialogProgress(shell);
				progress.run(true, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException {
						try {
							DialogProgress.monitor = monitor;
							monitor.beginTask("......", IProgressMonitor.UNKNOWN);
							objectWrapper.object = task.run();
						} catch (EmpException e) {
							e.printStackTrace();
							throw new InvocationTargetException(e);
						} catch (Exception e) {
							e.printStackTrace();
							throw new InvocationTargetException(e);
						}
					}
				});
			} else {
				objectWrapper.object = task.run();
			}

			return objectWrapper.object;
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof EmpException) {
				throw (EmpException) e.getCause();
			} else {
				throw new EmpException(ERROR_CODE.ERROR_UNKNOWN, e.getCause());
			}
		} catch (EmpException e) {
			throw e;
		} catch (Exception e) {
			throw new EmpException(ERROR_CODE.ERROR_UNKNOWN, e);
		}
	}

	/**
	 * 하위 작업을 설정합니다.
	 * 
	 * @param name
	 *            작업 이름
	 */
	public static void setSubTask(String name) {
		if (monitor != null) {
			monitor.subTask(name);
		}
	}

	private DialogProgress(Shell parent) {
		super(parent);
		super.setShellStyle(SWT.APPLICATION_MODAL | SWT.SYSTEM_MODAL | SWT.PRIMARY_MODAL);
	}

	@Override
	protected void createCancelButton(Composite parent) {
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		if (getParentShell() == null) {
			Rectangle parentRect = parent.getDisplay().getBounds();
			parent.setLocation((parentRect.width - WIDTH) / 2, (parentRect.height - HEIGHT) / 2);
		} else {
			Rectangle parentRect = getParentShell().getBounds();
			parent.setLocation(parentRect.x + (parentRect.width - WIDTH) / 2, parentRect.y + (parentRect.height - HEIGHT) / 2);
		}

		parent.setBackground(ThemeFactory.getColor(COLOR_ONION.DIALOG_BG));
		parent.setSize(WIDTH, HEIGHT);

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 10;
		gridLayout.marginBottom = 4;
		gridLayout.marginLeft = 8;
		gridLayout.marginRight = 8;
		parent.setLayout(gridLayout);

		final Composite panelMain = new Composite(parent, SWT.NONE);
		panelMain.setBackgroundMode(SWT.INHERIT_DEFAULT);
		panelMain.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_BG));
		panelMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		panelMain.setLayout(new GridLayout(2, false));

		Label labelImage = new Label(panelMain, SWT.NONE);
		labelImage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		labelImage.setImage(ThemeFactory.getImage(IMAGE_ONION.DIALOG_PROGRESS_ICON));

		progressIndicator = new ProgressIndicator(panelMain, SWT.NONE);
		progressIndicator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		subTaskLabel = new Label(panelMain, SWT.LEFT | SWT.WRAP);
		subTaskLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		subTaskLabel.setForeground(ThemeFactory.getColor(COLOR_ONION.PAGE_FG));

		return parent;
	}

}
