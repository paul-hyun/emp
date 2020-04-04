package com.hellonms.platforms.emp_orange.client_swt.page.fault.alarm_statistics;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.fault.event.Model4AlarmStatistics;

/**
 * <p>
 * ModelClient4AlarmStatisticsValue
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ModelClient4AlarmStatisticsValue {

	protected Date collectTime;

	protected Model4AlarmStatistics model4AlarmStatistics;

	public ModelClient4AlarmStatisticsValue(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Model4AlarmStatistics getModel4AlarmStatistics() {
		return model4AlarmStatistics;
	}

	public void setModel4AlarmStatistics(Model4AlarmStatistics model4AlarmStatistics) {
		this.model4AlarmStatistics = model4AlarmStatistics;
	}

}
