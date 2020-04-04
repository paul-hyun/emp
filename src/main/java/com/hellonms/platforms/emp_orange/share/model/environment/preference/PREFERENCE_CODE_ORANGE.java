package com.hellonms.platforms.emp_orange.share.model.environment.preference;

import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_CODE_ONION;
import com.hellonms.platforms.emp_onion.share.model.environment.preference.PREFERENCE_TYPE;

public class PREFERENCE_CODE_ORANGE extends PREFERENCE_CODE_ONION {

	public static final PREFERENCE_CODE PREFERENCE_ORANGE_NETWORK_NEDETAIL_DISPLAYORDER = PREFERENCE_CODE.add(0x10002001, "Network", "NeDetail", "Display Order", PREFERENCE_TYPE.STRING, 0, 999999, "NE detail display order");

}
