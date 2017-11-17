package com.example.spring.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@ComponentScan(basePackages = {"com.example.spring.mq"})
public class AMQConsumerConfig {
	@Autowired
	private AMQQueueConsumerAsyn amqQueueConsumerAsyn;
	@Bean
	public JmsTransactionManager jmsTransactionManager(){
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(connectionFactory());
		return jmsTransactionManager;
	}
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("failover://(tcp://127.0.0.1:61616)?randomize=false&jms.useAsyncSend=true&jms.prefetchPolicy.queuePrefetch=1");
		connectionFactory.setClientID(AMQCommonIdGenerator.genClientId());
		connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
		return connectionFactory;
	}
	@Bean
	public PooledConnectionFactory poolConnectionFactory(){
		PooledConnectionFactory poolConnectionFactory = new PooledConnectionFactory();
		poolConnectionFactory.setConnectionFactory(connectionFactory());
		poolConnectionFactory.setMaximumActive(1);
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
		defaultMessageListenerContainer.setConcurrentConsumers(21);
		defaultMessageListenerContainer.setSessionAcknowledgeMode(4);
		//session事务
		defaultMessageListenerContainer.setSessionTransacted(true);
		defaultMessageListenerContainer.setTransactionManager(jmsTransactionManager());
		return defaultMessageListenerContainer;
	}
	@Bean
	public RedeliveryPolicy redeliveryPolicy(){
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		//是否重新投递失败后，增长等待时间
		redeliveryPolicy.setUseExponentialBackOff(true);
		//设置重发次数
		redeliveryPolicy.setMaximumRedeliveries(5);
		//重发时间间隔
		redeliveryPolicy.setInitialRedeliveryDelay(2000);
		return redeliveryPolicy;
	}
}
