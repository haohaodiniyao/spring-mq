package com.example.activemq.queue;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 队列消费者
 * @author kaiyao
 *
 */
public class QueueConsumer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.157.151:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.SESSION_TRANSACTED);
		Destination destination = session.createQueue("queue.mytest");
		MessageConsumer consumer = session.createConsumer(destination);
		
		//TextMessage
		//TextMessage message = (TextMessage) consumer.receive();
		MapMessage message = (MapMessage)consumer.receive();
		int i = 1;
		while(message != null){
			//是否是消息重发
			boolean redelivered = message.getJMSRedelivered();
			System.out.println("消息重发:"+redelivered);
			System.out.println("收到属性:" + message.getStringProperty("extra"));
			System.out.println("收到消息:" + message.getString("message---"+i));
			System.out.println("-------------------------------------------");
//			session.commit();
			if(i == 1){
				/**
				 * 确认消息
				 * public static final int INDIVIDUAL_ACKNOWLEDGE = 4
				 * 单条消息确认
				 * 区别
				 * public static final int CLIENT_ACKNOWLEDGE = 2;
				 */
//				message.acknowledge();
				session.commit();
			}
//			message.acknowledge();
			//session事务提交
//			session.commit();
			i++;
		}
		try {
			TimeUnit.MINUTES.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		session.close();
		connection.close();
	}

}
