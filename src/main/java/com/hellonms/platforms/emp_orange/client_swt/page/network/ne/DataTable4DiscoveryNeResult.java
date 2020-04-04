package com.hellonms.platforms.emp_orange.client_swt.page.network.ne;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.Model4NeSessionIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.icmp.Model4NeSessionICMP;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP;

public class DataTable4DiscoveryNeResult extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SELECT), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_NAME), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_IP), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NE_TYPE), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SESSION_RESPONSE_TIME, MESSAGE_CODE_ORANGE.ICMP), //
			UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.SESSION_RESPONSE_TIME, MESSAGE_CODE_ORANGE.SNMP), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 50, 110, 110, 110, 135, 135 };

	/**
	 * 현재알람 모델 배열
	 */
	protected Model4Ne[] model4Nes = {};

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
		return SWT.NONE;
	}

	@Override
	public int getColumnWidth(int column) {
		return COLUMN_WIDTHS[column];
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model4Nes;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4Ne model4Ne = (Model4Ne) element;

		switch (columnIndex) {
		case 0:
			return "";
		case 1:
			return model4Ne.getNe_name();
		case 2:
			for (Model4NeSessionIf model4NeSession : model4Ne.getNeSessions()) {
				return model4NeSession.getHost();
			}
			return "";
		case 3:
			return model4Ne.getNe_code() == 0 ? "Unknown" : model4Ne.getNe_def().getProduct_class();
		case 4:
			for (Model4NeSessionIf model4NeSession : model4Ne.getNeSessions()) {
				if (model4NeSession.getProtocol().equals(Model4NeSessionICMP.PROTOCOL)) {
					Model4NeSessionICMP model4NeSessionICMP = (Model4NeSessionICMP) model4NeSession;
					return model4NeSessionICMP.isNe_session_state() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MILLISECOND, model4NeSessionICMP.getResponse_time()) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NO_RESPONSE);
				}
			}
			return "";
		case 5:
			for (Model4NeSessionIf model4NeSession : model4Ne.getNeSessions()) {
				if (model4NeSession.getProtocol().equals(Model4NeSessionSNMP.PROTOCOL)) {
					Model4NeSessionSNMP model4NeSessionSNMP = (Model4NeSessionSNMP) model4NeSession;
					return model4NeSessionSNMP.isNe_session_state() ? UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.MILLISECOND, model4NeSessionSNMP.getResponse_time()) : UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.NO_RESPONSE);
				}
			}
			return "";
		default:
			return "";
		}
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public void clear() {
		this.model4Nes = new Model4Ne[] {};
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4Ne[]) {
			setData((Model4Ne[]) datas[0]);
			refresh();
		}
	}

	protected void setData(Model4Ne[] model4Nes) {
		this.model4Nes = model4Nes;
	}

	@Override
	public Model4Ne[] getData() {
		return model4Nes;
	}

}
