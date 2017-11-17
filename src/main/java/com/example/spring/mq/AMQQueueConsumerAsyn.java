package com.example.spring.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
/**
 * 消费者
 * @author yaokai
 *
 */
@Component
public class AMQQueueConsumerAsyn implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("线程:"+Thread.currentThread()+"消费者:"+((TextMessage)message).getText());
			//消息确认
			message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
