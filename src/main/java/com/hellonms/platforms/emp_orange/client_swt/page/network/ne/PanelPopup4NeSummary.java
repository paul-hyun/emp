package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelImage;
import com.hellonms.platforms.emp_onion.client_swt.widget.label.LabelText;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelPopup;
import com.hellonms.platforms.emp_onion.client_swt.widget.page.PanelRound;
import com.hellonms.platforms.emp_onion.share.message.MESSAGE_CODE_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.util.resource.UtilResource4Orange;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;
import com.hellonms.platforms.emp_orange.theme.ThemeBuilder4Orange.IMAGE_ORANGE;

/**
 * <p>
 * PanelPopup4NeSummary
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class PanelPopup4NeSummary extends PanelPopup {

	/**
	 * NE 이미지
	 */
	protected LabelImage labelImage;

	/**
	 * NE명
	 */
	protected LabelText textNeName;

	/**
	 * NE IP
	 */
	protected LabelText textHost;

	/**
	 * ICMP 이미지
	 */
	protected LabelImage imageIcmp;

	/**
	 * ICMP 상태
	 */
	protected LabelText textIcmp;

	/**
	 * SNMP 이미지
	 */
	protected LabelImage imageSnmp;

	/**
	 * SNMP 상태
	 */
	protected LabelText textSnmp;

	/**
	 * 설명
	 */
	protected LabelText textDescription;

	/**
	 * 통신오류 개수
	 */
	protected LabelText labelCfCount;

	/**
	 * 심각 개수
	 */
	protected LabelText labelCriticalCount;

	/**
	 * 주의 개수
	 */
	protected LabelText labelMajorCount;

	/**
	 * 경계 개수
	 */
	protected LabelText labelMinorCount;

	/**
	 * 노드
	 */
	protected NODE node;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param node
	 *            노드
	 */
	public PanelPopup4NeSummary(Shell parent, NODE node) {
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

		LabelText labelNeName = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelNeName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelNeName.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_NAME) + ": ");

		textNeName = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textNeName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textNeName.widthHint = 260;
		textNeName.setLayoutData(gd_textNeName);

		LabelText labelHost = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelHost.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelHost.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_IP) + ": ");

		textHost = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textHost = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_textHost.widthHint = 260;
		textHost.setLayoutData(gd_textHost);

		LabelText labelIcmp = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelIcmp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelIcmp.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ICMP) + ": ");

		Composite panelIcmp = new Composite(panelOverview.getContentComposite(), SWT.NONE);
		GridLayout gl_panelIcmp = new GridLayout(2, false);
		gl_panelIcmp.verticalSpacing = 0;
		gl_panelIcmp.marginWidth = 0;
		gl_panelIcmp.marginHeight = 0;
		panelIcmp.setLayout(gl_panelIcmp);
		GridData gd_panelIcmp = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_panelIcmp.widthHint = 260;
		panelIcmp.setLayoutData(gd_panelIcmp);

		imageIcmp = new LabelImage(panelIcmp, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF));

		textIcmp = new LabelText(panelIcmp, SWT.WRAP);

		LabelText labelSnmp = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelSnmp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelSnmp.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SNMP) + ": ");

		Composite panelSnmp = new Composite(panelOverview.getContentComposite(), SWT.NONE);
		GridLayout gl_panelSnmp = new GridLayout(2, false);
		gl_panelSnmp.verticalSpacing = 0;
		gl_panelSnmp.marginWidth = 0;
		gl_panelSnmp.marginHeight = 0;
		panelSnmp.setLayout(gl_panelSnmp);
		GridData gd_panelSnmp = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_panelSnmp.widthHint = 260;
		panelSnmp.setLayoutData(gd_panelSnmp);

		imageSnmp = new LabelImage(panelSnmp, SWT.NONE, ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF));

		textSnmp = new LabelText(panelSnmp, SWT.WRAP);

		LabelText labelDescription = new LabelText(panelOverview.getContentComposite(), SWT.NONE);
		labelDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		labelDescription.setText(UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.DESCRIPTION) + ": ");

		textDescription = new LabelText(panelOverview.getContentComposite(), SWT.WRAP);
		GridData gd_textDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_textDescription.widthHint = 260;
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
		GridData gd_panelAlarm = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_panelAlarm.widthHint = 260;
		panelAlarm.setLayoutData(gd_panelAlarm);

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

		Model4Ne ne = (Model4Ne) node.getValue();
		Model4NeSessionIf[] neSessions = ne.getNeSessions();
		String host = UtilLanguage.getMessage(MESSAGE_CODE_ONION.UNKNOWN);
		Image icmpImage = null;
		String icmpText = UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NOT_MANAGE);
		Image snmpImage = null;
		String snmpText = UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NOT_MANAGE);
		for (Model4NeSessionIf neSession : neSessions) {
			if (neSession != null) {
				host = neSession.getHost();
				if (neSession.getProtocol().equals(Model4NeSessionICMP.PROTOCOL)) {
					icmpImage = neSession.isAdmin_state() ? (neSession.isNe_session_state() ? ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR) : ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF)) : null;
					icmpText = neSession.isAdmin_state() ? (neSession.isNe_session_state() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NORMAL) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ABNORMAL)) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NOT_MANAGE);
				} else if (neSession.getProtocol().equals(Model4NeSessionSNMP.PROTOCOL)) {
					snmpImage = neSession.isAdmin_state() ? (neSession.isNe_session_state() ? ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CLEAR) : ThemeFactory.getImage(IMAGE_ORANGE.FAULT_ALARM_CF)) : null;
					snmpText = neSession.isAdmin_state() ? (neSession.isNe_session_state() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NORMAL) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.ABNORMAL)) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NOT_MANAGE);
				}
			}
		}

		textNeName.setText(ne.getNe_name());
		textHost.setText(host);
		imageIcmp.setImage(icmpImage);
		textIcmp.setText(icmpText);
		imageSnmp.setImage(snmpImage);
		textSnmp.setText(snmpText);
		textDescription.setText(ne.getDescription());

		labelCfCount.setText(String.valueOf(node.getCf()));
		labelCriticalCount.setText(String.valueOf(node.getCritical()));
		labelMajorCount.setText(String.valueOf(node.getMajor()));
		labelMinorCount.setText(String.valueOf(node.getMinor()));
	}

}
