package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.hellonms.platforms.emp_onion.client_swt.widget.page.Panel;
import com.hellonms.platforms.emp_onion.theme.ThemeBuilder4Onion.COLOR_ONION;
import com.hellonms.platforms.emp_onion.theme.ThemeFactory;
import com.hellonms.platforms.emp_orange.client_swt.page.network.menu.MenuPopup4Network;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_tree.ModelClient4NetworkTree.NODE;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.Panel4NetworkViewAt;
import com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.ne.Panel4NetworkViewNe.Panel4NetworkViewNeListenerIf;
import com.hellonms.platforms.emp_orange.client_swt.widget.page.PageNode.ToolbarItemIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS.NetInterface;

/**
 * <p>
 * Panel4NetworkViewNe
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class Panel4NetworkViewNMS extends Panel4NetworkViewAt {

	/**
	 * 콘텐츠
	 */
	protected Composite content;

	/**
	 * 노드
	 */
	protected NODE node;

	private ArrayList<Panel> panel_list;

	private Panel4NetworkViewNMSChart panel4Cpu;

	private Panel4NetworkViewNMSChart panel4Mem;

	private Panel4NetworkViewNMSChart panel4Net;

	private Panel4NetworkViewNMSTable panel4Partition;

	/**
	 * 생성자 입니다.
	 * 
	 * @param parent
	 *            부모 컴포지트
	 * @param style
	 *            스타일
	 * @param listener
	 *            리스너
	 */
	public Panel4NetworkViewNMS(Composite parent, int style, Panel4NetworkViewNeListenerIf listener) {
		super(parent, style, listener);
		layout_panel();
	}

	@Override
	protected Control createContent(Composite parent) {
		content = new Composite(parent, SWT.NONE);
		content.setBackground(ThemeFactory.getColor(COLOR_ONION.PAGE_FG));
		content.setBackgroundMode(SWT.INHERIT_DEFAULT);
		content.setLayout(new FormLayout());

		panel_list = new ArrayList<Panel>();

		panel4Cpu = new Panel4NetworkViewNMSChart(content, SWT.NONE, Panel4NetworkViewNMSChart.CPU);
		panel_list.add(panel4Cpu);

		panel4Net = new Panel4NetworkViewNMSChart(content, SWT.NONE, Panel4NetworkViewNMSChart.NET);
		panel_list.add(panel4Net);

		panel4Mem = new Panel4NetworkViewNMSChart(content, SWT.NONE, Panel4NetworkViewNMSChart.MEM);
		panel_list.add(panel4Mem);

		panel4Partition = new Panel4NetworkViewNMSTable(content, SWT.NONE, Panel4NetworkViewNMSTable.PARTITION);
		panel_list.add(panel4Partition);

		return content;
	}

	@Override
	protected MenuPopup4Network createMenuPopup4Network(Shell shell) {
		return null;
	}

	@Override
	protected NODE getNode() {
		return node;
	}

	@Override
	protected NODE getSelectedNode() {
		return node;
	}

	@Override
	public void display(boolean progressBar) {
		if (node != null && node.isNe()) {
			((Panel4NetworkViewNeListenerIf) listener).queryNetworkViewNe(node.getId(), progressBar);
		}
	}

	@Override
	public void display(NODE node, boolean progressBar) {
		if (node.isNe()) {
			this.node = node;

			display(progressBar);
		}
	}

	@Override
	public void monitor() {
		if (!isDisposed() && node != null && node.isNe()) {
			((Panel4NetworkViewNeListenerIf) listener).queryNetworkViewNe(node.getId(), false);
		}
	}

	@Override
	public void display(final NODE node, final Object object) {
		if (Thread.currentThread() == getDisplay().getThread()) {
			displayDetail(node, object);
		} else {
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					displayDetail(node, object);
				}
			});
		}
	}

	protected void displayDetail(NODE node, Object object) {
		if (object instanceof Model4ResourceNMS[] && 0 < ((Model4ResourceNMS[]) object).length) {
			Model4ResourceNMS[] resource_nmss = (Model4ResourceNMS[]) object;
			panel4Cpu.display(resource_nmss, new String[] { resource_nmss[resource_nmss.length - 1].getCpu_usage_display() });
			panel4Mem.display(resource_nmss, new String[] { resource_nmss[resource_nmss.length - 1].getMem_usage_display() });
			NetInterface[] net_interfaces = resource_nmss[resource_nmss.length - 1].getNet_interfaces();
			if (0 < net_interfaces.length) {
				panel4Net.display(resource_nmss, new String[] { net_interfaces[0].net_interface_bps_display(net_interfaces[0].net_interface_rx_bytes_diff), net_interfaces[0].net_interface_bps_display(net_interfaces[0].net_interface_tx_bytes_diff) });
			}
			panel4Partition.display(resource_nmss);
		}
	}

	@Override
	public void layout() {
		layout_panel();
		super.layout();
	}

	private void layout_panel() {
		int height = 205;
		Panel control_left = null;
		Panel control_right = null;
		int index = 0;
		for (Panel panel : this.panel_list) {
			if (index % 2 == 0) {
				FormData fd = new FormData();
				fd.top = control_left == null ? new FormAttachment(0, 5) : new FormAttachment(control_left, 5, SWT.BOTTOM);
				fd.left = new FormAttachment(0, 5);
				fd.right = new FormAttachment(50, -3);
				fd.bottom = control_left == null ? new FormAttachment(0, height) : new FormAttachment(control_left, height, SWT.BOTTOM);
				panel.setLayoutData(fd);
				control_left = panel;
			} else {
				FormData fd = new FormData();
				fd.top = control_right == null ? new FormAttachment(0, 5) : new FormAttachment(control_right, 5, SWT.BOTTOM);
				fd.left = new FormAttachment(50, 3);
				fd.right = new FormAttachment(100, -5);
				fd.bottom = control_right == null ? new FormAttachment(0, height) : new FormAttachment(control_right, height, SWT.BOTTOM);
				panel.setLayoutData(fd);
				control_right = panel;
			}
			index++;
		}

		Point size = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		computeScroll(new Point(-1, size.y));
		content.layout();
	}

	@Override
	protected ToolbarItemIf[] getToolbarItems() {
		return new ToolbarItemIf[] {};
	}

	@Override
	public void blink(boolean blink) {
	}

}
