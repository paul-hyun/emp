/**
 * Copyright 2015 Hello NMS. All rights reserved.
 */
package com.hellonms.platforms.emp_orange.share.model.security.operation_log;

import java.util.Date;

import com.hellonms.platforms.emp_core.share.model.ModelIf;
import com.hellonms.platforms.emp_core.share.operation.OPERATION_CODE;

/**
 * <p>
 * 운용이력 모델
 * </p>
 * 
 * @since 1.6
 * @create 2015. 3. 11.
 * @modified 2015. 3. 11.
 * @author cchyun
 */
@SuppressWarnings("serial")
public class Model4OperationLog implements ModelIf {

	/**
	 * 트렌젝션 ID
	 */
	private long transaction_id;

	/**
	 * Ne Group ID
	 */
	private int ne_group_id;

	/**
	 * Ne ID
	 */
	private int ne_id;

	/**
	 * 사용자 ID
	 */
	private int user_id;

	/**
	 * 사용자 계정
	 */
	private String user_account;

	/**
	 * 사용자 세션 ID
	 */
	private int user_session_id;

	/**
	 * 사용자 IP
	 */
	private String user_ip;

	/**
	 * 운용이력 정의 코드
	 */
	private OPERATION_CODE operation_code;

	/**
	 * 수행결과
	 */
	private boolean result;

	/**
	 * 실패원인
	 */
	private String fail_cause;

	/**
	 * OPERATION 시작시간
	 */
	private Date start_time;

	/**
	 * OPERATION 종료시간
	 */
	private Date end_time;

	/**
	 * 설명
	 */
	private String description;

	/**
	 * 생성한 사람
	 */
	private String creator;

	/**
	 * 생성 시간
	 */
	private Date create_time;

	/**
	 * 마지막 수정한 사람
	 */
	private String updater;

	/**
	 * 마직막 수정 시간
	 */
	private Date update_time;

	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getNe_id() {
		return ne_id;
	}

	public void setNe_id(int ne_id) {
		this.ne_id = ne_id;
	}

	public int getNe_group_id() {
		return ne_group_id;
	}

	public void setNe_group_id(int ne_group_id) {
		this.ne_group_id = ne_group_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public int getUser_session_id() {
		return user_session_id;
	}

	public void setUser_session_id(int user_session_id) {
		this.user_session_id = user_session_id;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public OPERATION_CODE getOperation_code() {
		return operation_code;
	}

	public void setOperation_code(OPERATION_CODE operation_code) {
		this.operation_code = operation_code;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getFail_cause() {
		return fail_cause;
	}

	public void setFail_cause(String fail_cause) {
		this.fail_cause = fail_cause;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public ModelIf copy() {
		Model4OperationLog model = new Model4OperationLog();
		model.transaction_id = this.transaction_id;
		model.ne_id = this.ne_id;
		model.ne_group_id = this.ne_group_id;
		model.user_id = this.user_id;
		model.user_session_id = this.user_session_id;
		model.user_account = this.user_account;
		model.user_ip = this.user_ip;
		model.operation_code = this.operation_code;
		model.result = this.result;
		model.fail_cause = this.fail_cause;
		model.start_time = this.start_time;
		model.end_time = this.end_time;
		model.description = this.description;
		model.creator = this.creator;
		model.create_time = this.create_time;
		model.updater = this.updater;
		model.update_time = this.update_time;
		return model;
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String indent) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(indent).append(getClass().getName()).append(S_LB).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("transaction_id").append(S_DL).append(transaction_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_group_id").append(S_DL).append(ne_group_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("ne_id").append(S_DL).append(ne_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_id").append(S_DL).append(user_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_session_id").append(S_DL).append(user_session_id).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_account").append(S_DL).append(user_account).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("user_ip").append(S_DL).append(user_ip).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("operation_code").append(S_DL).append(operation_code).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("result").append(S_DL).append(result).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("fail_cause").append(S_DL).append(fail_cause).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("start_time").append(S_DL).append(start_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("end_time").append(S_DL).append(end_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("description").append(S_DL).append(description).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("creator").append(S_DL).append(creator).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("create_time").append(S_DL).append(create_time).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("updater").append(S_DL).append(updater).append(S_NL);
		stringBuilder.append(indent).append(S_TB).append("update_time").append(S_DL).append(update_time).append(S_NL);
		stringBuilder.append(indent).append(S_RB);
		return stringBuilder.toString();
	}

	@Override
	public String toDisplayString(String indent) {
		return toString(indent);
	}

}
