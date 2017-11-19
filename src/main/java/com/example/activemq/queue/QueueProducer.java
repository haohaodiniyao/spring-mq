package com.example.activemq.queue;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 队列生产者
 * @author kaiyao
 *
 */
public class QueueProducer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.CLIENT_ACKNOWLEDGE);
		TemporaryQueue tq = session.createTemporaryQueue();
		Destination destination = session.createQueue("queue.mytest");
		MessageProducer producer = session.createProducer(destination);
		for(int i=1;i<5;i++){
			//TextMessage
			//TextMessage message = session.createTextMessage("this is test message "+i);
			//MapMessage
			MapMessage message = session.createMapMessage();
			message.setStringProperty("extra", "okok");
			message.setString("message---"+i, "my map message 777 "+i);
			message.setJMSReplyTo(tq);
			producer.send(message);	
		}
		session.commit();
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
		connection.close();
	}

}
