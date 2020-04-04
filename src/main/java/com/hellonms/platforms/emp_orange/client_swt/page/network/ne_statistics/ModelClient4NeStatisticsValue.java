package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_statistics;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatisticsIf;

/**
 * <p>
 * ModelClient4NeStatisticsValue
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ModelClient4NeStatisticsValue {

	protected Date collectTime;

	protected Model4NeStatisticsIf model4NeStatistics;

	public ModelClient4NeStatisticsValue(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public Model4NeStatisticsIf getModel4NeStatistics() {
		return model4NeStatistics;
	}

	public void setModel4NeStatistics(Model4NeStatisticsIf model4NeStatistics) {
		this.model4NeStatistics = model4NeStatistics;
	}

}