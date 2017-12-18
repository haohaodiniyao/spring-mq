package com.example.activemq.queue2;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

public class QueueProducer2 {

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("hello1.queue");
		MessageProducer messageProducer = session.createProducer(destination);
		for(int i=0;i<3;i++){
			TextMessage textMessage = session.createTextMessage("this is text message ccc "+i);
			messageProducer.send(textMessage);
		}
		session.commit();
		session.close();
		connection.close();
	}

}
