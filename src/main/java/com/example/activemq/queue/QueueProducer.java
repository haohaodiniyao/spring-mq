package com.example.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
/**
 * 队列生产者
 * @author kaiyao
 *
 */
public class QueueProducer {

	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.157.151:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		/**
		 *  创建会话
		 *  boolean transacted, int acknowledgeMode
		 *  事务性会话 transacted=true 默认 acknowledgeMode = SESSION_TRANSACTED = 0
		 *  非事务性会话 transacted=false acknowledgeMode不能为0
		 *  
		 *	public static final int AUTO_ACKNOWLEDGE = 1;
		 *  public static final int CLIENT_ACKNOWLEDGE = 2;
		 *  public static final int DUPS_OK_ACKNOWLEDGE = 3;
		 *  public static final int INDIVIDUAL_ACKNOWLEDGE = 4;
		 *  public static final int SESSION_TRANSACTED = 0; 
		 *  
		 **/
		Session session = connection.createSession(true, ActiveMQSession.SESSION_TRANSACTED);
        /**
         * 目的地
         */
		Destination destination = session.createQueue("queue.mytest");
		/**
		 * 生产者
		 */
		MessageProducer producer = session.createProducer(destination);
		int size = 1;
		for(int i=1;i<size;i++){
			//TextMessage
			//TextMessage message = session.createTextMessage("this is test message "+i);
			//MapMessage
			MapMessage message = session.createMapMessage();
			/**
			 * 消息属性
			 */
			message.setStringProperty("extra", "okok");
			/**
			 * 消息内容
			 */
			message.setString("message---"+i, "my map message eee "+i);
			producer.send(message);	
		}
		session.commit();
		session.close();
		connection.close();
	}

}
