/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.util;

import com.hellonms.platforms.emp_orange.share.model.emp_model.EMP_MODEL_NE_INFO;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeInfo;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeStatistics;
import com.hellonms.platforms.emp_orange.share.model.network.ne_info.Model4NeThreshold;

/**
 * <p>
 * </p>
 *
 * @since 1.6
 * @create 2015. 7. 9.
 * @modified 2015. 7. 9.
 * @author cchyun
 *
 */
public interface ModelFactory4NeInfoIf {

	public Model4NeInfo newNeInfo(EMP_MODEL_NE_INFO ne_info_code);

	public Model4NeStatistics newNeStatistics(EMP_MODEL_NE_INFO ne_info_code);

	public Model4NeThreshold newNeThreshold(EMP_MODEL_NE_INFO ne_info_code);

}
