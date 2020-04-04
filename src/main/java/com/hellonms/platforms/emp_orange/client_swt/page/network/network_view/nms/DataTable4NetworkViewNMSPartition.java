package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS.FileSystem;
import com.hellonms.platforms.emp_util.number.UtilNumber;

/**
 * <p>
 * DataTable4NeInfo
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4NetworkViewNMSPartition extends DataTableAt {

	protected final String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.PARTITION), "Total", "Used", "Usage" };

	protected Model4ResourceNMS resource_nms;

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public String getColumnTitle(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getColumnStyle(int column) {
		switch (column) {
		case 0:
			return SWT.NONE;
		default:
			return SWT.RIGHT;
		}
	}

	@Override
	public int getColumnWidth(int column) {
		return 100;
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public void clear() {
		resource_nms = null;
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4ResourceNMS[]) {
			setData((Model4ResourceNMS[]) datas[0]);
			refresh();
		}
	}

	protected void setData(Model4ResourceNMS[] resource_nmss) {
		if (0 < resource_nmss.length) {
			this.resource_nms = resource_nmss[resource_nmss.length - 1];
		} else {
			this.resource_nms = null;
		}
	}

	@Override
	public Object getData() {
		return resource_nms;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return resource_nms == null ? new Object[] {} : resource_nms.getFile_systems();
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		FileSystem file_system = (FileSystem) element;
		switch (columnIndex) {
		case 0:
			return file_system.file_system_name;
		case 1:
			return UtilNumber.to1024String(file_system.file_system_size * 1024);
		case 2:
			return UtilNumber.to1024String(file_system.file_system_used * 1024);
		case 3:
			return UtilNumber.format(file_system.file_system_usage * 100) + " %";
		}
		return "";
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

}
