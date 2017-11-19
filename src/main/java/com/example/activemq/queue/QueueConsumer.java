package com.example.activemq.queue;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 队列消费者
 * @author kaiyao
 *
 */
public class QueueConsumer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Enumeration enumeration = connection.getMetaData().getJMSXPropertyNames();
		while(enumeration.hasMoreElements()){
			System.out.println(enumeration.nextElement()+"#");
		}
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(false, ActiveMQSession.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue.mytest");
		MessageConsumer consumer = session.createConsumer(destination);
		for(int i=1;i<5;i++){
			//TextMessage
			//TextMessage message = (TextMessage) consumer.receive();
			MapMessage message = (MapMessage)consumer.receive();
			System.out.println(message.getJMSReplyTo());
			System.out.println("JMSRedelivered:"+message.getJMSRedelivered());
			System.out.println("属性:" + message.getStringProperty("extra"));
			System.out.println("消息:" + message.getString("message---"+i));
			if(i == 4){
				message.acknowledge();
			}
			//session事务提交
//			session.commit();
		}
		session.close();
		connection.close();
	}

}
