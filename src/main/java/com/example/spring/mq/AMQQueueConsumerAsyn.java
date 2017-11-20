package com.example.spring.mq;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
/**
 * 消费者
 * @author yaokai
 *
 */
@Component
public class AMQQueueConsumerAsyn implements SessionAwareMessageListener<TextMessage> {

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		try {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"线程:"+Thread.currentThread()+"消费者:"+message.getText());
			int a = 1/0;
			//消息确认
			message.acknowledge();
		} catch (JMSException e) {
			session.rollback();
			e.printStackTrace();
		}
		
	}

	

}
