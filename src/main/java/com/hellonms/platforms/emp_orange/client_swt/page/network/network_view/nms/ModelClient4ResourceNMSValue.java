package com.hellonms.platforms.emp_orange.client_swt.page.network.network_view.nms;

import java.util.Date;

import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4ResourceNMS;

/**
 * <p>
 * ModelClient4ResourceNMSValue
 * </p>
 *
 * @since 1.6
 * @create 2015. 5. 19.
 * @modified 2015. 6. 3.
 * @author jungsun
 */
public class ModelClient4ResourceNMSValue {

	protected Date collectTime;

	protected Model4ResourceNMS resource_nms;

	public ModelClient4ResourceNMSValue(Date collectTime) {
		this.collectTime = collectTime;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public Model4ResourceNMS getResource_nms() {
		return resource_nms;
	}

	public void setResource_nms(Model4ResourceNMS resource_nms) {
		this.resource_nms = resource_nms;
	}

}