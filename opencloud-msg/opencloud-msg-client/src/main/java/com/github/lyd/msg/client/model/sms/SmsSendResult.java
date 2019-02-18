package com.github.lyd.msg.client.model.sms;

/**
 * @author woodev
 */
public class SmsSendResult {

	private boolean success;

	private String code;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
