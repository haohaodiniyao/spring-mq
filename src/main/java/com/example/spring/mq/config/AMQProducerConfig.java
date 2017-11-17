package com.example.spring.mq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@ComponentScan(basePackages={"com.example.spring.mq.config"})
public class AMQProducerConfig {
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("failover://(tcp://127.0.0.1:61616)?randomize=false&jms.useAsyncSend=true");
		return activeMQConnectionFactory;
	}
	@Bean
	public CachingConnectionFactory cachingConnectionFactory(){
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(connectionFactory());
		cachingConnectionFactory.setSessionCacheSize(10);
		return cachingConnectionFactory;
	}
	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(cachingConnectionFactory());
		//默认的目的地名称
		jmsTemplate.setDefaultDestinationName("subject");
		//是否持久化发送的消息
		//jmsTemplate.setDeliveryMode(deliveryMode);
		jmsTemplate.setDeliveryPersistent(false);
		//此消费通道是否为topic
		jmsTemplate.setPubSubDomain(false);
		//消息确认机制
		jmsTemplate.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
		//Spring-JmsTemplate特性设置
		//https://www.cnblogs.com/ywjy/p/5434146.html
		//服务质量开关
		jmsTemplate.setExplicitQosEnabled(true);
		//消息存活时间毫秒 7天
		jmsTemplate.setTimeToLive(604800000);
		return jmsTemplate;
	}
	@Bean
	public ActiveMQQueue queueDestination(){
		ActiveMQQueue activeMQQueue = new ActiveMQQueue("queue.mytest");
		return activeMQQueue;
	}
}
