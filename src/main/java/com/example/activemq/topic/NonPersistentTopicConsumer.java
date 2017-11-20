package com.example.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 非持久化topic消息消费者
 * @author kaiyao
 *
 */
public class NonPersistentTopicConsumer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.157.151:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
		Destination destination = session.createTopic("mytopic");
		MessageConsumer consumer = session.createConsumer(destination);
		TextMessage message = (TextMessage)consumer.receive();
		while(message != null){
			System.out.println("topic consumer:"+message.getText());
			message = (TextMessage)consumer.receive();
		}
		session.close();
		connection.close();
	}

}
