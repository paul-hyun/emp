/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.lock;

import java.util.concurrent.locks.Lock;

import com.google.common.util.concurrent.Striped;

/**
 * <p>
 * 키를 이용해서 걸리는 락
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 21.
 * @modified 2015. 4. 21.
 * @author cchyun
 *
 */
public class UtilKeyLock<K> {

	private Striped<Lock> locks = Striped.lock(16);

	public void lock(K key) {
		locks.get(key).lock();
	}

	public void unlock(K key) {
		locks.get(key).unlock();
	}

}
