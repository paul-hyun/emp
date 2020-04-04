package com.hellonms.platforms.emp_orange.client_swt.model_display;

import java.io.Serializable;

import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfoIf;
import com.hellonms.platforms.emp_util.string.UtilString;

@SuppressWarnings("serial")
public class ModelDisplay4NeInfo implements Serializable {

	private Model4Ne model4Ne;

	private int ne_info_code;

	private TablePageConfig<Model4NeInfoIf> tablePageConfigNeInfo;

	public Model4Ne getModel4Ne() {
		return model4Ne;
	}

	public void setModel4Ne(Model4Ne model4Ne) {
		this.model4Ne = model4Ne;
	}

	public EMP_MODEL_NE_INFO getNe_info_def() {
		EMP_MODEL_NE_INFO ne_info_def = EMP_MODEL.current().getNe_info(ne_info_code);
		if (ne_info_def == null) {
			throw new RuntimeException(UtilString.format("unknown info: '{}'", ne_info_code));
		}
		return ne_info_def;
	}

	public int getNe_info_code() {
		return ne_info_code;
	}

	public void setNe_info_code(int ne_info_code) {
		this.ne_info_code = ne_info_code;
	}

	public TablePageConfig<Model4NeInfoIf> getTablePageConfigNeInfo() {
		return tablePageConfigNeInfo;
	}

	public void setTablePageConfigNeInfo(TablePageConfig<Model4NeInfoIf> tablePageConfigNeInfo) {
		this.tablePageConfigNeInfo = tablePageConfigNeInfo;
	}

	public Model4NeInfoIf[] getModel4NeInfos() {
		if (tablePageConfigNeInfo != null && tablePageConfigNeInfo.getCount() > 0) {
			return tablePageConfigNeInfo.values;
		}
		return new Model4NeInfoIf[0];
	}

	public Model4NeInfoIf getModel4NeInfo(int index) {
		Model4NeInfoIf[] model4NeInfos = getModel4NeInfos();
		if (model4NeInfos != null && 0 <= index && index < model4NeInfos.length) {
			return model4NeInfos[index];
		}
		return null;
	}

}
