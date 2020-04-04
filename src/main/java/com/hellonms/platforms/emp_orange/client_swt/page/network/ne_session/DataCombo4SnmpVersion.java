package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_session;

import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.combo.DataComboAt;
import com.hellonms.platforms.emp_orange.share.model.network.ne_session.snmp.Model4NeSessionSNMP.SNMP_VERSION;

/**
 * <p>
 * DataCombo4SnmpVersion
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataCombo4SnmpVersion extends DataComboAt {

	/**
	 * SNMP 버전 배열
	 */
	protected final SNMP_VERSION[] ITEMS = SNMP_VERSION.values();

	@Override
	public Object[] getElements(Object inputElement) {
		return ITEMS;
	}

	@Override
	public Object getInput() {
		return this;
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		return String.valueOf(element);
	}

	@Override
	public void setDatas(Object... datas) {
	}

	@Override
	public Object getDefaultItem() {
		return SNMP_VERSION.V2c;
	}

	@Override
	public Object getItem(int index) {
		if (ITEMS != null && -1 < index && index < ITEMS.length) {
			return ITEMS[index];
		}
		return null;
	}

	@Override
	public Object findItem(Object element) {
		if (element instanceof SNMP_VERSION) {
			for (SNMP_VERSION aaa : ITEMS) {
				if (aaa.equals(element)) {
					return aaa;
				}
			}
		}
		return null;
	}

}