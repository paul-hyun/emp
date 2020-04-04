package com.hellonms.platforms.emp_orange.client_swt.model_display;

import java.io.Serializable;

import com.hellonms.platforms.emp_onion.share.model.STATISTICS_TYPE;
import com.hellonms.platforms.emp_onion.share.model.TablePageConfig;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.NE_INFO_INDEX;
import com.hellonms.platforms.emp_util.string.UtilString;

@SuppressWarnings("serial")
public class ModelDisplay4NeStatistics implements Serializable {

	private Model4Ne model4Ne;

	private int ne_info_code;

	private STATISTICS_TYPE statisticsType;

	private NE_INFO_INDEX[] ne_info_indexs;

	private NE_INFO_INDEX ne_info_index;

	private Model4NeStatisticsIf[] model4NeStatisticses;

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

	public STATISTICS_TYPE getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(STATISTICS_TYPE statisticsType) {
		this.statisticsType = statisticsType;
	}

	public NE_INFO_INDEX[] getNe_info_indexs() {
		return ne_info_indexs;
	}

	public void setNe_info_indexs(NE_INFO_INDEX[] ne_info_indexs) {
		this.ne_info_indexs = ne_info_indexs;
	}

	public NE_INFO_INDEX getNe_info_index() {
		return ne_info_index;
	}

	public void setNe_info_index(NE_INFO_INDEX ne_info_index) {
		this.ne_info_index = ne_info_index;
	}

	public Model4NeStatisticsIf[] getModel4NeStatisticses() {
		if (model4NeStatisticses == null) {
			return new Model4NeStatisticsIf[0];
		}
		return model4NeStatisticses;
	}

	public void setModel4NeStatisticses(Model4NeStatisticsIf[] model4NeStatisticses) {
		this.model4NeStatisticses = model4NeStatisticses;
	}

	public TablePageConfig<Model4NeStatisticsIf> getTablePageConfigNeStatistics() {
		TablePageConfig<Model4NeStatisticsIf> pageConfig = new TablePageConfig<Model4NeStatisticsIf>(0, model4NeStatisticses.length, model4NeStatisticses, model4NeStatisticses.length);
		return pageConfig;
	}

}
