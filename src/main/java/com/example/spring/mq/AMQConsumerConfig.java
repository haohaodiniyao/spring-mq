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
		connectionFactory.setBrokerURL("failover://(tcp://192.168.157.151:61616)?randomize=false&jms.useAsyncSend=true&jms.prefetchPolicy.queuePrefetch=1");
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
		defaultMessageListenerContainer.setSessionAcknowledgeMode(2);
		//session事务
//		defaultMessageListenerContainer.setSessionTransacted(true);
//		defaultMessageListenerContainer.setTransactionManager(jmsTransactionManager());
		return defaultMessageListenerContainer;
	}
	@Bean
	public RedeliveryPolicy redeliveryPolicy(){
		//https://www.cnblogs.com/olmlo/p/4708660.html
		//http://activemq.apache.org/redelivery-policy.html
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		//启用倍数递增方式增加延迟时间 默认false
		redeliveryPolicy.setUseExponentialBackOff(true);
		//重连时间间隔递增倍数默认5
		redeliveryPolicy.setBackOffMultiplier(3);
		//最大重传次数(-1 不限 0 不进行重传)
		redeliveryPolicy.setMaximumRedeliveries(5);
		//初始重发延迟时间
		redeliveryPolicy.setInitialRedeliveryDelay(1000);
		//启用防止冲突功能 默认false
		redeliveryPolicy.setUseCollisionAvoidance(true);
		short collisionAvoidancePercent = 15;
		//设置防止冲突范围的正负百分比
		redeliveryPolicy.setCollisionAvoidancePercent(collisionAvoidancePercent);
		return redeliveryPolicy;
	}
}
