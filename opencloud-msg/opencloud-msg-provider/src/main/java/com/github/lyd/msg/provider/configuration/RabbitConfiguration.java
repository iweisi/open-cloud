/*
 * MIT License
 *
 * Copyright (c) 2018 yadu.liu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.github.lyd.msg.provider.configuration;

import com.github.lyd.msg.client.constants.MessageConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mq配置
 * @author liuyadu
 */
@Configuration
public class RabbitConfiguration {
    /**
     * 消息中心实际消费队列交换配置
     *
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(MessageConstants.EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 消息中心实际消费队列配置
     *
     * @return
     */
    @Bean
    public Queue queueMsg() {
        return new Queue(MessageConstants.QUEUE_MSG, true);
    }

    /**
     * 消息中心实际消息交换与队列绑定
     *
     * @return
     */
    @Bean
    Binding bindingMsg() {
        return BindingBuilder
                .bind(queueMsg())
                .to(directExchange())
                .with(MessageConstants.RK_MSG);
    }

    /**
     * 延迟消息配置交换器
     *
     * @return
     */
    @Bean
    DirectExchange directExchangeDelay() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(MessageConstants.EXCHANGE_DELAY)
                .durable(true)
                .build();
    }

    /**
     * 延迟消息配置Delay队列
     *
     * @return
     */
    @Bean
    Queue queueMsgDelay() {
        return QueueBuilder
                .durable(MessageConstants.QUEUE_MSG_DELAY)
                // 默认消息过期时间
                .withArgument("x-message-ttl", 3600000)
                // 配置到期后转发的交换
                .withArgument("x-dead-letter-exchange", MessageConstants.EXCHANGE)
                // 配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key", MessageConstants.RK_MSG)
                .build();
    }

    /**
     * 延迟消息配置Delay绑定实际消息中心实际消费交换机
     *
     * @return
     */
    @Bean
    public Binding bindingMsgDelay() {
        return BindingBuilder
                .bind(queueMsgDelay())
                .to(directExchangeDelay())
                .with(MessageConstants.RK_MSG_DELAY);
    }
}
