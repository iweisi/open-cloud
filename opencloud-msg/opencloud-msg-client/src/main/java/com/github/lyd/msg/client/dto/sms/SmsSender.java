package com.github.lyd.msg.client.dto.sms;

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
