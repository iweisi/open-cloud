package com.github.lyd.msg.client.model.sms;

/**
 * @author woodev
 */
public interface SmsSender {

	/**
	 * 发送短信
	 * @param parameter
	 * @return
	 */
	SmsSendResult send(SmsParameter parameter);
}
