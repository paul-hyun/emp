/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import com.hellonms.platforms.emp_core.share.error.EmpException;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt;
import com.hellonms.platforms.emp_orange.client_swt.driver.Driver4OrangeClientSwt.Driver4OrangeClientSwtTaskIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtIf;
import com.hellonms.platforms.emp_orange.client_swt.servlet.Invoker4OrangeClientSwtReqeust;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne.Model4Ne;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Page4NeThresholdAdvisor
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public class Page4NeThresholdAdvisor {

	public EMP_MODEL_NE_INFO[] getListNe_threshold_code(EMP_MODEL_NE ne_def) throws EmpException {
		return EMP_MODEL.current().getNe_thresholds_by_ne(ne_def == null ? 0 : ne_def.getCode());
	}

	public Model4NeThresholdIf queryNeThreshold(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def) throws EmpException {
		return (Model4NeThresholdIf) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.queryNeThreshold(request, model4Ne.getNe_id(), ne_info_def.getCode());
			}
		});
	}

	public Model4NeThresholdIf updateNeThreshold(final Model4NeThresholdIf ne_threshold, final EMP_MODEL_NE_INFO_FIELD ne_info_field_def) throws EmpException {
		return (Model4NeThresholdIf) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.updateNeThreshold(request, ne_threshold, ne_info_field_def.getName());
			}
		});
	}

	public Model4NeThresholdIf[] copyListNeThreshold(final Model4Ne model4Ne, final EMP_MODEL_NE_INFO ne_info_def, final int[] ne_id_targets) throws EmpException {
		return (Model4NeThresholdIf[]) Driver4OrangeClientSwt.getInstance().execute(new Driver4OrangeClientSwtTaskIf() {
			@Override
			public Object run(Invoker4OrangeClientSwtIf invoker, Invoker4OrangeClientSwtReqeust request) throws EmpException {
				return invoker.copyListNeThreshold(request, model4Ne.getNe_id(), ne_info_def.getCode(), ne_id_targets);
			}
		});
	}

}
