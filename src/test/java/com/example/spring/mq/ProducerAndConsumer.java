package com.example.spring.mq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.spring.mq.config.AMQQueueSender;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:spring-producer2.xml","classpath:spring-consumer2.xml"})
public class ProducerAndConsumer {
	@Autowired
	private AMQQueueSender amqQueueSender;
	@Test
	public void test() {
		for(int i=1;i<10000;i++){
			try {
				amqQueueSender.send(i+"");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}