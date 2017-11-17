package com.example.spring.mq.config;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
/**
 * 生产者
 * @author yaokai
 *
 */
@Component
public class AMQQueueSender {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination queueDestination;

	public void send(final String msg) throws InterruptedException {
		this.jmsTemplate.send(this.queueDestination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				System.out.println("生产者:" + msg);
				return session.createTextMessage(msg);
			}
		});
	}
}
