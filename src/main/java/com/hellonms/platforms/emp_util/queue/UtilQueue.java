/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_util.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * Queue
 * </p>
 *
 * @since 1.6
 * @create 2015. 4. 10.
 * @modified 2015. 4. 10.
 * @author cchyun
 *
 */
public class UtilQueue<T> {

	/**
	 * Lock 객체
	 */
	private final ReentrantLock lock = new ReentrantLock();

	/**
	 * Lock Condition 객체 (wait 시 사용)
	 */
	private final Condition condition = lock.newCondition();

	/**
	 * 객체를 저장할 리스트
	 */
	private final LinkedList<T> queue = new LinkedList<T>();

	/**
	 * Queue에 저장할 최대 개수<br>
	 * 최대 개수를 초과하면 가장 오래된 데이터가 삭제되고 새로운 데이터가 추가됩니다.
	 */
	private final int maxSize;

	/**
	 * maxSize * 1.1<br>
	 * 성능을 위해 Queue가 10% 초과할 경우 삭제 한다.
	 */
	private final int upperSize;

	/**
	 * <p>
	 * 생성자<br>
	 * 기본 최대 Queue에 저장할 최대 개수는 10,000개 입니다.
	 * </p>
	 */
	public UtilQueue() {
		this(10000);
	}

	/**
	 * <p>
	 * 생성자
	 * </p>
	 * 
	 * @param maxSize
	 *            Queue에 저장할 최대 개수
	 */
	public UtilQueue(int maxSize) {
		this.maxSize = maxSize;
		this.upperSize = maxSize + maxSize / 10;
	}

	/**
	 * <p>
	 * queue로부터 객체를 꺼내어 옵니다. (FIFO).<br>
	 * 만을 queue가 비어 있으면 queue가 찰때까지 Thread는 block 됩니다.
	 * </p>
	 * 
	 * @return 꺼내온 객체
	 */
	public T pop() {
		lock.lock();
		try {
			while (queue.isEmpty()) {
				try {
					condition.await();
				} catch (InterruptedException e) {
				}
			}

			T obj = queue.removeFirst();
			if (!queue.isEmpty()) {
				condition.signal();
			}
			return obj;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * <p>
	 * 새로운 객체를 quque에 추가합니다.<br>
	 * queue full이 발생하면 처음 등록된 객체부터 순서대로 삭제 합니다.
	 * </p>
	 * 
	 * @param object
	 *            추가할 객체
	 */
	public void push(T object) {
		lock.lock();
		try {
			if (queue.size() > upperSize) {
				while (queue.size() > maxSize) {
					queue.removeFirst();
				}
			}
			queue.addLast(object);
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * <p>
	 * Queue 사이즈를 조회한다.
	 * </p>
	 * 
	 * @return
	 */
	public int size() {
		return queue.size();
	}

}
