package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_core.share.language.UtilLanguage;
import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.share.message.MESSAGE_CODE_ORANGE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD.THRESHOLD_TYPE;
import com.hellonms.platforms.emp_orange.share.model.fault.event.SEVERITY;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataTable4NeThreshold
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4NeThreshold extends DataTableAt {

	/**
	 * 컬럼명 배열
	 */
	protected String[] COLUMN_NAMES = { //
	UtilLanguage.getMessage(MESSAGE_CODE_ORANGE.FIELD), //
			SEVERITY.CRITICAL.name(), //
			SEVERITY.MAJOR.name(), //
			SEVERITY.MINOR.name(), //
	};

	/**
	 * 컬럼 너비 배열
	 */
	protected int[] COLUMN_WIDTHS = { 120, 210, 210, 210 };

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD[] ne_info_fields = {};

	protected Model4NeThresholdIf ne_info_threshold;;

	public DataTable4NeThreshold(EMP_MODEL_NE_INFO ne_info_def) {
		this.ne_info_def = ne_info_def;
		List<EMP_MODEL_NE_INFO_FIELD> ne_info_field_list = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field : ne_info_def.getNe_thresholds()) {
			if (ne_info_field.isDisplay_enable()) {
				ne_info_field_list.add(ne_info_field);
			}
		}
		this.ne_info_fields = ne_info_field_list.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
	}

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
	public void clear() {
		ne_info_threshold = null;
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof Model4NeThresholdIf) {
			setData((Model4NeThresholdIf) datas[0]);
			refresh();
		}
	}

	protected void setData(Model4NeThresholdIf ne_info_threshold) {
		this.ne_info_threshold = ne_info_threshold;
	}

	@Override
	public Object getData() {
		return ne_info_threshold;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return ne_info_fields;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		EMP_MODEL_NE_INFO_FIELD ne_info_field = (EMP_MODEL_NE_INFO_FIELD) element;
		if (ne_info_threshold != null) {
			switch (columnIndex) {
			case 0:
				return UtilString.isEmpty(ne_info_field.getDisplay_name()) ? ne_info_field.getName() : ne_info_field.getDisplay_name();
			case 1:
				return toString(ne_info_field.getThr_type(), ne_info_threshold.getThreshold_critical_min(ne_info_field), ne_info_threshold.getThreshold_critical_max(ne_info_field));
			case 2:
				return toString(ne_info_field.getThr_type(), ne_info_threshold.getThreshold_major_min(ne_info_field), ne_info_threshold.getThreshold_major_max(ne_info_field));
			case 3:
				return toString(ne_info_field.getThr_type(), ne_info_threshold.getThreshold_minor_min(ne_info_field), ne_info_threshold.getThreshold_minor_max(ne_info_field));
			}
		}
		return "";
	}

	private String toString(THRESHOLD_TYPE threshold_type, Long min, Long max) {
		if (min != null && max != null) {
			StringBuilder stringBuilder = new StringBuilder();
			switch (threshold_type) {
			case GT:
				stringBuilder.append("#").append(" > ").append(max);
				break;
			case LT:
				stringBuilder.append("#").append(" < ").append(min);
				break;
			case BT:
				stringBuilder.append(min).append(" < ").append("#").append(" < ").append(max);
				break;
			case OT:
				stringBuilder.append("#").append(" < ").append(min).append("  OR  ").append(max).append(" < ").append("#");
				break;
			}
			return stringBuilder.toString();
		} else {
			return "--";
		}
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
