/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model;

import java.io.Serializable;

/**
 * <p>
 * Insert description of TablePageConfig.java
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 17.
 * @modified 2015. 3. 17.
 * @author jungsun
 * 
 */
@SuppressWarnings("serial")
public class TablePageConfig<T> implements Serializable {

	public int totalCount;

	public int startNo;

	public int count;

	public T[] values;

	public TablePageConfig() {
	}

	public TablePageConfig(int startNo, int count, T[] values, int totalCount) {
		this.startNo = startNo;
		this.count = count;
		this.values = values;
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getStartNo() {
		return startNo;
	}

	public int getCount() {
		return count;
	}

	public T[] getValues() {
		return values;
	}

}
