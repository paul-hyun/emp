/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.pool;

import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Pool
 * </p>
 *
 * @since 1.6
 * @create 2015. 12. 30.
 * @modified 2015. 12. 30.
 * @author cchyun
 */
public class UtilPool<T> {

	public interface UtilPoolFactoryIf<T> {

		public T createObject() throws EmpException;

	}

	/**
	 * Lock 객체
	 */
	private final ReentrantLock lock = new ReentrantLock();

	/**
	 * 객체를 저장할 리스트 (전체)
	 */
	private final LinkedList<T> pool = new LinkedList<T>();

	/**
	 * Pool에 저장할 최대 개수<br>
	 */
	private final int maxSize;

	/**
	 * Pool에 현재 개수<br>
	 */
	private int curSize;

	/**
	 * Object 생성자
	 */
	private UtilPoolFactoryIf<T> factory;

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param maxSize
	 *            Pool에 저장할 최대 개수
	 */
	public UtilPool(int maxSize, UtilPoolFactoryIf<T> factory) {
		this.maxSize = maxSize;
		this.factory = factory;
	}

	/**
	 * <p>
	 * pool로부터 객체를 꺼내어 옵니다. (FIFO).<br>
	 * 만을 pool가 비어 있으면 pool가 찰때까지 Thread는 block 됩니다.
	 * </p>
	 * 
	 * @return 꺼내온 객체
	 * @throws EmpException
	 */
	public T borrowObject() throws EmpException {
		lock.lock();
		try {
			if (pool.isEmpty()) {
				if (curSize < maxSize) {
					pool.addLast(factory.createObject());
				} else {
					throw new EmpException(ERROR_CODE_CORE.RESOURCE_OVERFLOW, "UtilPool", maxSize);
				}
			}

			T obj = pool.removeFirst();
			return obj;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * <p>
	 * 새로운 객체를 quque에 추가합니다.<br>
	 * pool full이 발생하면 처음 등록된 객체부터 순서대로 삭제 합니다.
	 * </p>
	 * 
	 * @param object
	 *            추가할 객체
	 */
	public void returnObject(T object) {
		lock.lock();
		try {
			pool.addLast(object);
		} finally {
			lock.unlock();
		}
	}

}
