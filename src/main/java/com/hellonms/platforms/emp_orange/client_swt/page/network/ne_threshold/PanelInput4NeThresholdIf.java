/**
 * Copyright 2016 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.client_swt.page.network.ne_threshold;

import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO_FIELD;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThresholdIf;

/**
 * <p>
 * Panel4NeThresholdDataAt
 * </p>
 *
 * @since 1.6
 * @create 2016. 1. 22.
 * @modified 2016. 1. 22.
 * @author jungsun
 */
public interface PanelInput4NeThresholdIf {

	public void display(Model4NeThresholdIf model4NeThreshold);

	public int getStartNo();

	public Model4NeThresholdIf getSelected();

	public EMP_MODEL_NE_INFO_FIELD getNe_field_code();

	public boolean isNeedWizard();

	public boolean isComplete();

	public String getErrorMessage();

	public void clear();

}
