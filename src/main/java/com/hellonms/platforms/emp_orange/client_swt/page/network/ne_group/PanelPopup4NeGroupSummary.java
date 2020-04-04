package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_group;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne_group.Model4NeGroup;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;

/**
 * <p>
 * PanelPopup4NeGroupSummary
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup4NeGroupSummary extends PanelPopup {

	/**
	 * 이미지 라벨
	 */
	protected LabelImage labelImage;

	/**
	 * NE그룹명 라벨
	 */
	protected LabelText textNeGroupName;

	/**
	 * NE그룹 개수 라벨
	 */
	protected LabelText textNeGroupCount;

	/**
	 * NE 개수 라벨
	 */
	protected LabelText textNeCount;

	/**
	 * 설명 라벨
	 */
	protected LabelText textDescription;

	/**
	 * 통신오류 알람 개수 라벨
	 */
	protected LabelText labelCfCount;

	/**
	 * 심각 알람 개수 라벨
	 */
	protected LabelText labelCriticalCount;

	/**
	 * 주의 알람 개수 라벨
	 */
	protected LabelText labelMajorCount;

	/**
	 * 경계 알람 개수 라벨
	 */
	protected LabelText labelMinorCount;

	/**
	 * 노드
	 */
	protected NODE node;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent	부모 컴포지트
	 * @param node		노드
	 */
	public PanelPopup4NeGroupSummary(Shell parent, NODE node) {
		super(parent);
		this.node = node;

		createGUI();
		display();
		pack();
	}

	/**
	 * GUI를 생성합니다.
	 */
	protected void createGUI() {
		setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_BG));

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(ThemeFactory.getColor(COLOR_ONION.WORKBENCH_BG));
				e.gc.drawRectangle(0, 0, getSize().x - 1, getSize().y - 1);
			}
		});

		setLayout(new FormLayout());

		Composite panelContents = new Composite(this, SWT.NONE);
		panelContents.setBackground(ThemeFactory.getColor(COLOR_ONION.PANEL_CONTENTS_BG));
		panelContents.setBackgroundMode(SWT.INHERIT_DEFAULT);
		panelContents.setLayout(new FormLayout());
		FormData fd_panelContents = new FormData();
		fd_panelContents.bottom = new FormAttachment(100, -5);
		fd_panelContents.right = new FormAttachment(100, -5);
		fd_panelContents.top = new FormAttachment(0, 5);
		fd_panelContents.left = new FormAttachment(0, 5);
		panelContents.setLayoutData(fd_panelContents);

		labelImage = new LabelImage(panelContents, SWT.NONE);
		FormData fd_labelImage = new FormData();
		fd_labelImage.top = new FormAttachment(0, 5);
		fd_labelImage.left = new FormAttachment(0, 5);
		labelImage.setLayoutData(fd_labelImage);

		PanelRound panelOverview = new PanelRound(panelContents, SWT.NONE);
		FormData fd_panelOverview = new FormData();
		fd_panelOverview.bottom = new FormAttachment(100, -5);
		fd_panelOverview.right = new FormAttachment(100, -5);
		fd_panelOverview.left = new FormAttachment(labelImage, 10);
		fd_panelOverview.top = new FormAttachment(labelImage, 0, SWT.TOP);
		panelOverview.setLayoutData(fd_panelOverview);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		gridLayout.verticalSpacing = 8;
		gridLayout.horizontalSpacing = 10;
		panelOverview.getContentComposite().setLayout(gridLayout);

		LabelText labelNeGroupName = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelNeGroupName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelNeGroupName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_GROUP_NAME) + ": ");

		textNeGroupName = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textNeGroupName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textNeGroupName.widthHint = 200;
		textNeGroupName.setLayoutData(gd_textNeGroupName);

		LabelText labelNeGroupCount = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelNeGroupCount.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelNeGroupCount.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHILD_NE_GROUP_COUNT) + ": ");

		textNeGroupCount = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textNeGroupCount = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textNeGroupCount.widthHint = 200;
		textNeGroupCount.setLayoutData(gd_textNeGroupCount);

		LabelText labelNeCount = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelNeCount.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelNeCount.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.CHILD_NE_COUNT) + ": ");

		textNeCount = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textNeCount = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textNeCount.widthHint = 200;
		textNeCount.setLayoutData(gd_textNeCount);

		LabelText labelDescription = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelDescription.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION) + ": ");

		textDescription = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textDescription.widthHint = 200;
		textDescription.setLayoutData(gd_textDescription);

		LabelText labelAlarm = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelAlarm.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelAlarm.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ALARM) + ": ");

		Composite panelAlarm = new Composite(panelOverview.getContentComposite(), SWT.NONE);
		GridLayout gl_panelAlarm = new GridLayout(8, false);
		gl_panelAlarm.verticalSpacing = 0;
		gl_panelAlarm.marginWidth = 0;
		gl_panelAlarm.marginHeight = 0;
		panelAlarm.setLayout(gl_panelAlarm);
		panelAlarm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		@SuppressWarnings("unused")
		LabelImage labelCfImage = new LabelImage(panelAlarm, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF));

		labelCfCount = new LabelText(panelAlarm, SWT.NONE);
		labelCfCount.setText("0");	

		LabelImage labelCriticalImage = new LabelImage(panelAlarm, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CRITICAL));
		GridData gd_labelCriticalImage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelCriticalImage.horizontalIndent = 10;
		labelCriticalImage.setLayoutData(gd_labelCriticalImage);

		labelCriticalCount = new LabelText(panelAlarm, SWT.NONE);
		labelCriticalCount.setText("0");

		LabelImage labelMajorImage = new LabelImage(panelAlarm, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MAJOR));
		GridData gd_labelMajorImage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelMajorImage.horizontalIndent = 10;
		labelMajorImage.setLayoutData(gd_labelMajorImage);

		labelMajorCount = new LabelText(panelAlarm, SWT.NONE);
		labelMajorCount.setText("0");

		LabelImage labelMinorImage = new LabelImage(panelAlarm, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_MINOR));
		GridData gd_labelMinorImage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelMinorImage.horizontalIndent = 10;
		labelMinorImage.setLayoutData(gd_labelMinorImage);

		labelMinorCount = new LabelText(panelAlarm, SWT.NONE);
		labelMinorCount.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		labelMinorCount.setText("0");
	}

	/**
	 * 화면에 출력합니다.
	 */
	protected void display() {
		labelImage.setImage(UtilResource4Orange.getNetworkDetailIcon(node.getIcon(), node.getSeverity()));

		Model4NeGroup neGroup = (Model4NeGroup) node.getValue();
		int neGroupCount = 0;
		int neCount = 0;
		try {
			for (NODE child : ModelClient4NetworkTree.getInstance().getListChild(neGroup.getNe_group_id())) {
				if (child.isNeGroup()) {
					neGroupCount++;
				} else if (child.isNe()) {
					neCount++;
				}
			}
		} catch (EmpException e) {
			e.printStackTrace();
		}

		textNeGroupName.setText(neGroup.getNe_group_name());
		textNeGroupCount.setText(String.valueOf(neGroupCount));
		textNeCount.setText(String.valueOf(neCount));
		textDescription.setText(neGroup.getDescription());

		labelCfCount.setText(String.valueOf(node.getCf()));
		labelCriticalCount.setText(String.valueOf(node.getCritical()));
		labelMajorCount.setText(String.valueOf(node.getMajor()));
		labelMinorCount.setText(String.valueOf(node.getMinor()));
	}

}
