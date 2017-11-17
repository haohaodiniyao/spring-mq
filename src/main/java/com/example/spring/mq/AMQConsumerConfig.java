package com.example.spring.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@ComponentScan(basePackages = {"com.example.spring.mq"})
public class AMQConsumerConfig {
	@Autowired
	private AMQQueueConsumerAsyn amqQueueConsumerAsyn;
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("failover://(tcp://127.0.0.1:61616)?randomize=false&jms.useAsyncSend=true&jms.prefetchPolicy.queuePrefetch=1");
		connectionFactory.setClientID(AMQCommonIdGenerator.genClientId());
		return connectionFactory;
	}
	@Bean
	public PooledConnectionFactory poolConnectionFactory(){
		PooledConnectionFactory poolConnectionFactory = new PooledConnectionFactory();
		poolConnectionFactory.setConnectionFactory(connectionFactory());
		poolConnectionFactory.setMaximumActive(10);
		return poolConnectionFactory;
	}
	@Bean
	public ActiveMQQueue queueDestination(){
		ActiveMQQueue activeMQQueue = new ActiveMQQueue("queue.mytest");
		return activeMQQueue;
	}
	@Bean
	public DefaultMessageListenerContainer defaultMessageListenerContainer(){
		DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(poolConnectionFactory());
		defaultMessageListenerContainer.setDestination(queueDestination());
		defaultMessageListenerContainer.setMessageListener(amqQueueConsumerAsyn);
		defaultMessageListenerContainer.setConcurrentConsumers(5);
		defaultMessageListenerContainer.setSessionAcknowledgeMode(4);
		return defaultMessageListenerContainer;
	}
}
