package com.hellonms.platforms.emp_onion.client_swt.widget.selector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.hellonms.platforms.emp_onion.client_swt.widget.text.TextInput4Integer64;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.IMAGE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;

/**
 * <p>
 * SelectorPage
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class SelectorPage extends Composite {

	/**
	 * 페이지 이동 리스너의 인터페이스 입니다.
	 * 
	 * @since Version 1.4.0
	 * @author Hello NMS
	 */
	public interface SelectorPageListenerIf {

		/**
		 * 페이지 정보가 변경되었을 경우 호출됩니다.
		 * 
		 * @param startNo
		 *            페이지의 첫 항목의 번호
		 * @param count
		 *            페이지 당 최대 항목 개수
		 */
		public void load(int startNo, int count);

	}

	private TextInput4Integer64 textPage;

	private GridData gd_textPage;

	private Label labelTotalPage;

	private GridData gd_labelTotalPage;

	private Label labelDescription;

	private int rowCount;

	private int totalCount;

	private int startNo;

	private SelectorPageListenerIf listener;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param rowCount
	 *            페이지 당 최대 항목 개수
	 * @param listener
	 *            페이지 이동 리스너
	 */
	public SelectorPage(Composite parent, int style, int rowCount, SelectorPageListenerIf listener) {
		super(parent, style);
		this.rowCount = rowCount;
		this.listener = listener;

		createGUI();
	}

	private void createGUI() {
		GridLayout gridLayout = new GridLayout(9, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		Label labelFirst = new Label(this, SWT.NONE);
		labelFirst.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (SelectorPage.this.listener != null) {
					SelectorPage.this.listener.load(0, SelectorPage.this.rowCount);
				}
			}
		});
		labelFirst.setImage(ThemeFactory.getImage(IMAGE_ONION.SELECTOR_PAGE_FIRST));

		Label labelPrev = new Label(this, SWT.NONE);
		labelPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (SelectorPage.this.listener != null) {
					int startNo = SelectorPage.this.startNo - SelectorPage.this.rowCount;
					SelectorPage.this.listener.load(startNo < 0 ? 0 : startNo, SelectorPage.this.rowCount);
				}
			}
		});
		labelPrev.setImage(ThemeFactory.getImage(IMAGE_ONION.SELECTOR_PAGE_PREV));

		Label labelPage = new Label(this, SWT.NONE);
		labelPage.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelPage.setText("Page");

		textPage = new TextInput4Integer64(this, SWT.BORDER | SWT.RIGHT);
		textPage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				try {
					int pageNo = Integer.parseInt(textPage.getText());
					int startNo = (pageNo < 1) ? 0 : (pageNo - 1) * SelectorPage.this.rowCount;

					listener.load(startNo, SelectorPage.this.rowCount);
				} catch (Exception ex) {
				}
			}
		});
		textPage.setText("0");
		gd_textPage = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textPage.widthHint = 32;
		textPage.setLayoutData(gd_textPage);

		Label labelOf = new Label(this, SWT.NONE);
		labelOf.setText("of");

		labelTotalPage = new Label(this, SWT.RIGHT);
		gd_labelTotalPage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelTotalPage.widthHint = 32;
		labelTotalPage.setLayoutData(gd_labelTotalPage);
		labelTotalPage.setText("0");

		Label labelNext = new Label(this, SWT.NONE);
		labelNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (SelectorPage.this.listener != null) {
					int startNo = SelectorPage.this.startNo + SelectorPage.this.rowCount;
					SelectorPage.this.listener.load(startNo >= totalCount ? SelectorPage.this.startNo : startNo, SelectorPage.this.rowCount);
				}
			}
		});
		labelNext.setImage(ThemeFactory.getImage(IMAGE_ONION.SELECTOR_PAGE_NEXT));

		Label labelLast = new Label(this, SWT.NONE);
		labelLast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (SelectorPage.this.listener != null) {
					int startNo = (totalCount / SelectorPage.this.rowCount + (totalCount % SelectorPage.this.rowCount == 0 ? -1 : 0)) * SelectorPage.this.rowCount;
					SelectorPage.this.listener.load(startNo < 0 ? 0 : startNo, SelectorPage.this.rowCount);
				}
			}
		});
		labelLast.setImage(ThemeFactory.getImage(IMAGE_ONION.SELECTOR_PAGE_LAST));

		labelDescription = new Label(this, SWT.NONE);
		GridData gd_labelDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_labelDescription.horizontalIndent = 5;
		labelDescription.setLayoutData(gd_labelDescription);
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * 페이지 정보를 초기화 합니다.
	 */
	public void initialize() {
		totalCount = 0;
		startNo = 0;
		int count = 0;

		if (rowCount == 0 || totalCount == 0) {
			textPage.setText("0");
			textPage.setMax_value(0);
			labelTotalPage.setText("0");
			labelDescription.setText("(0~0 of 0)");
		} else {
			int pageNo = startNo / rowCount + 1;
			int totalPageNo = totalCount / rowCount + (totalCount % rowCount == 0 ? 0 : 1);

			textPage.setText(String.valueOf(pageNo));
			textPage.setMax_value(totalPageNo);
			labelTotalPage.setText(String.valueOf(totalPageNo));
			labelDescription.setText("(" + (startNo + 1) + "~" + (startNo + count) + " of " + totalCount + ")");
		}
	}

	/**
	 * 테이블의 페이지를 그립니다.
	 * 
	 * @param pageConfig
	 *            페이지 정보
	 */
	public void display(TablePageConfig<?> pageConfig) {
		totalCount = pageConfig.totalCount;
		startNo = pageConfig.startNo;
		int count = pageConfig.getCount();
		int width = 32;

		if (rowCount == 0 || totalCount == 0) {
			textPage.setText("0");
			textPage.setMax_value(0);
			labelTotalPage.setText("0");
			labelDescription.setText("(0~0 of 0)");
		} else {
			int pageNo = startNo / rowCount + 1;
			int totalPageNo = totalCount / rowCount + (totalCount % rowCount == 0 ? 0 : 1);
			int length = (totalPageNo + "").length();
			width = length > 4 ? length * 3 + 32 : 32;

			textPage.setText(String.valueOf(pageNo));
			textPage.setMax_value(totalPageNo);
			labelTotalPage.setText(String.valueOf(totalPageNo));
			labelDescription.setText("(" + (startNo + 1) + "~" + (startNo + count) + " of " + totalCount + ")");
		}

		gd_textPage.widthHint = width;
		gd_labelTotalPage.widthHint = width;
		layout();
	}

	/**
	 * 페이지 당 항목의 최대 개수를 반환합니다.
	 * 
	 * @return 페이지 당 항목의 최대 개수
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 현재 페이지의 첫 항목의 번호를 반환합니다.
	 * 
	 * @return 항목의 번호
	 */
	public int getStartNo() {
		return startNo;
	}

}
