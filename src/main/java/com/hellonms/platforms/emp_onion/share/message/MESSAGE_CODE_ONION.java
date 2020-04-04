/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.message;

import com.hellonms.platforms.emp_core.share.message.MESSAGE_CODE;
import com.hellonms.platforms.emp_core.share.message.MESSAGE_CODE_CORE;


/**
 * <p>
 * Insert description of MESSAGE_CODE_ONION.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 17.
 * @modified 2015. 3. 17.
 * @author jungsun
 * 
 */
public class MESSAGE_CODE_ONION extends MESSAGE_CODE_CORE {

	/**
	 * Unknown
	 */
	public static final MESSAGE_CODE UNKNOWN = new MESSAGE_CODE("MESSAGE_ONION_UNKNOWN", "Unknown");

	/**
	 * Ok
	 */
	public static final MESSAGE_CODE OK = new MESSAGE_CODE("MESSAGE_ONION_OK", "Ok");

	/**
	 * Cancel
	 */
	public static final MESSAGE_CODE CANCEL = new MESSAGE_CODE("MESSAGE_ONION_CANCEL", "Cancel");
	
	/**
	 * Day
	 */
	public static final MESSAGE_CODE DAY = new MESSAGE_CODE("MESSAGE_ONION_DAY", "Day");
	
	/**
	 * Hour
	 */
	public static final MESSAGE_CODE HOUR = new MESSAGE_CODE("MESSAGE_ONION_HOUR", "Hour");
	
	/**
	 * Minute
	 */
	public static final MESSAGE_CODE MINUTE = new MESSAGE_CODE("MESSAGE_ONION_MINUTE", "Min");
	
	/**
	 * Second
	 */
	public static final MESSAGE_CODE SECOND = new MESSAGE_CODE("MESSAGE_ONION_SECOND", "Sec");
	
	/**
	 * Finish
	 */
	public static final MESSAGE_CODE FINISH = new MESSAGE_CODE("MESSAGE_ONION_FINISH", "Finish");
	
	/**
	 * Sun
	 */
	public static final MESSAGE_CODE SUN = new MESSAGE_CODE("MESSAGE_ONION_SUN", "Sun");
	
	/**
	 * Mon
	 */
	public static final MESSAGE_CODE MON = new MESSAGE_CODE("MESSAGE_ONION_MON", "Mon");
	
	/**
	 * Tue
	 */
	public static final MESSAGE_CODE TUE = new MESSAGE_CODE("MESSAGE_ONION_TUE", "Tue");
	
	/**
	 * Wed
	 */
	public static final MESSAGE_CODE WED = new MESSAGE_CODE("MESSAGE_ONION_WED", "Wed");
	
	/**
	 * Thu
	 */
	public static final MESSAGE_CODE THU = new MESSAGE_CODE("MESSAGE_ONION_THU", "Thu");
	
	/**
	 * Fri
	 */
	public static final MESSAGE_CODE FRI = new MESSAGE_CODE("MESSAGE_ONION_FRI", "Fri");
	
	/**
	 * Sat
	 */
	public static final MESSAGE_CODE SAT = new MESSAGE_CODE("MESSAGE_ONION_SAT", "Sat");

}
