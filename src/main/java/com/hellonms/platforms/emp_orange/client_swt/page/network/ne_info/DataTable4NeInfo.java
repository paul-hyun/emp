package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hellonms.platforms.emp_onion.client_swt.data.table.DataTableAt;
import com.hellonms.platforms.emp_orange.client_swt.model_display.ModelDisplay4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_ENUM_FIELD;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.string.UtilString;

/**
 * <p>
 * DataTable4Info
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class DataTable4NeInfo extends DataTableAt {

	protected EMP_MODEL_NE_INFO ne_info_def;

	protected EMP_MODEL_NE_INFO_FIELD[] column_names;

	protected Model4NeInfoIf[] model4NeInfos = {};

	public DataTable4NeInfo(EMP_MODEL_NE_INFO ne_info_def) {
		this.ne_info_def = ne_info_def;
		initColumn();
	}

	protected void initColumn() {
		List<EMP_MODEL_NE_INFO_FIELD> neFieldCodeList = new ArrayList<EMP_MODEL_NE_INFO_FIELD>();
		for (EMP_MODEL_NE_INFO_FIELD ne_info_field_def : ne_info_def.getNe_info_fields()) {
			if (ne_info_field_def.isRead() && ne_info_field_def.isDisplay_enable()) {
				neFieldCodeList.add(ne_info_field_def);
			}
		}
		column_names = neFieldCodeList.toArray(new EMP_MODEL_NE_INFO_FIELD[0]);
	}

	@Override
	public int getColumnCount() {
		return column_names.length;
	}

	@Override
	public String getColumnTitle(int column) {
		String title = UtilString.isEmpty(column_names[column].getDisplay_name()) ? column_names[column].getName() : column_names[column].getDisplay_name();
		if (column < column_names.length) {
			if (UtilString.isEmpty(column_names[column].getUnit())) {
				return UtilString.format("{}", title);
			} else {
				return UtilString.format("{} ({})", title, column_names[column].getUnit());
			}
		}
		return "";
	}

	@Override
	public int getColumnStyle(int column) {
		return SWT.NONE;
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
		model4NeInfos = new Model4NeInfoIf[0];
	}

	@Override
	public void setDatas(Object... datas) {
		if (datas.length == 1 && datas[0] instanceof ModelDisplay4NeInfo) {
			setData((ModelDisplay4NeInfo) datas[0]);
			refresh();
		}
	}

	protected void setData(ModelDisplay4NeInfo modelDisplay4Info) {
		model4NeInfos = modelDisplay4Info.getModel4NeInfos();
	}

	@Override
	public Object getData() {
		return model4NeInfos;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model4NeInfos;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Model4NeInfoIf model4Info = (Model4NeInfoIf) element;
		if (model4Info != null && columnIndex < column_names.length) {
			Serializable neFieldValue = model4Info.getField_value(column_names[columnIndex]);
			if (neFieldValue != null) {
				EMP_MODEL_NE_INFO_FIELD ne_info_field_def = column_names[columnIndex];
				if (ne_info_field_def.getEnum_code() != 0) {
					EMP_MODEL_ENUM_FIELD enum_field = EMP_MODEL.current().getEnum_field(ne_info_field_def.getEnum_code(), String.valueOf(neFieldValue));
					if (enum_field != null) {
						return enum_field.getName();
					}
				} else {
					String value = model4Info.getField_value_display(column_names[columnIndex]);
					if (value != null) {
						return value;
					}
				}
			}
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
