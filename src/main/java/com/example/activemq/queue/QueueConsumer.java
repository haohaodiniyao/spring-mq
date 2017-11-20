package com.example.activemq.queue;

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
		Session session = connection.createSession(false, ActiveMQSession.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue.mytest");
		MessageConsumer consumer = session.createConsumer(destination);
		for(int i=1;i<5;i++){
			//TextMessage
			//TextMessage message = (TextMessage) consumer.receive();
			MapMessage message = (MapMessage)consumer.receive();
			System.out.println("收到属性:" + message.getStringProperty("extra"));
			System.out.println("收到消息:" + message.getString("message---"+i));
			if(i == 4){
				/**
				 * 确认消息
				 * public static final int INDIVIDUAL_ACKNOWLEDGE = 4
				 * 单条消息确认
				 * 区别
				 * public static final int CLIENT_ACKNOWLEDGE = 2;
				 */
				message.acknowledge();
			}
			//session事务提交
//			session.commit();
		}
		session.close();
		connection.close();
	}

}
