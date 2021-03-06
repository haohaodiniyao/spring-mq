package com.example.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 持久化topic消息消费者
 * @author kaiyao
 *
 */
public class PersistentTopicConsumer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		//clientID 2-1 ***************
		connection.setClientID("cc1");
		
		// boolean transacted, int acknowledgeMode
		// 创建会话
		Session session = connection.createSession(true, ActiveMQSession.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("mytopic2");
		//2-2 必须先运行注册**************
		TopicSubscriber ts = session.createDurableSubscriber(topic, "T1");
		connection.start();
//		TextMessage message = (TextMessage)ts.receive();
//		while(message != null){
//			System.out.println("topic persistent consumer:"+message.getText());
//			message = (TextMessage)ts.receive();
//		}
		
		for(int i=0;i<3;i++){
			TextMessage message = (TextMessage)ts.receive();
			System.out.println(message.getText());
		}
		
		session.commit();
		session.close();
		connection.close();
	}

}
