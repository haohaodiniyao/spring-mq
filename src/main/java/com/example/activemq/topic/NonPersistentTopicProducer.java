package com.example.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 非持久化topic消息生产者
 * @author kaiyao
 *
 */
public class NonPersistentTopicProducer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.157.151:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("mytopic");
		MessageProducer producer = session.createProducer(destination);
		for(int i=1;i<5;i++){
			//TextMessage
			TextMessage message = session.createTextMessage("this is test topic message "+i);
			producer.send(message);	
		}
		session.commit();
		session.close();
		connection.close();
	}

}
