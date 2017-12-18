package com.example.activemq.queue2;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

public class QueueConsumer2 {

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
		Destination destination = session.createQueue("hello1.queue");
		MessageConsumer messageConsumer = session.createConsumer(destination);
		for(int i=0;i<3;i++){
			TextMessage textMessage = (TextMessage)messageConsumer.receive();
			System.out.println(textMessage.getText());
			textMessage.acknowledge();
		}
//		session.commit();
		session.close();
		connection.close();
	}

}
