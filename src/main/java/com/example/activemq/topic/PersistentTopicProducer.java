package com.example.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 持久化消息生产者
 * @author kaiyao
 *
 */
public class PersistentTopicProducer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();

		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("mytopic");
		MessageProducer producer = session.createProducer(destination);
		//1-1 持久化topic***************
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		connection.start();
		for(int i=1;i<5;i++){
			//TextMessage
			TextMessage message = session.createTextMessage("this is test message "+i);
			producer.send(message);	
		}
		session.commit();
		session.close();
		connection.close();
	}

}
