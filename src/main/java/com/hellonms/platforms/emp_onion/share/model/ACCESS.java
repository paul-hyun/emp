/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_onion.share.model;

/**
 * <p>
 * 모델 접근권한 정의
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 12.
 * @modified 2015. 3. 12.
 * @author cchyun
 * 
 */
public enum ACCESS {

	/**
	 * 모델을 접근할 수 없다. 조회, 수정, 삭제 불가
	 */
	ACCESS_NONE(false, false, false, false),

	/**
	 * 조회만 가능하다. 수정 삭제는 불가
	 */
	ACCESS_READ(false, true, false, false),

	/**
	 * 조회, 수정만 가능하다. 삭제는 불가
	 */
	ACCESS_WRITE(false, true, true, false),

	/**
	 * 조회, 수정, 삭제가 가능하다.
	 */
	ACCESS_DELETE(false, true, true, true);

	private boolean create;

	private boolean read;

	private boolean update;

	private boolean delete;

	private ACCESS(boolean create, boolean read, boolean update, boolean delete) {
		this.create = create;
		this.read = read;
		this.update = update;
		this.delete = delete;
	}

	public boolean isCreate() {
		return create;
	}

	public boolean isRead() {
		return read;
	}

	public boolean isUpdate() {
		return update;
	}

	public boolean isDelete() {
		return delete;
	}

}
