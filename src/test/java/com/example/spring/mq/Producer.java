package com.example.spring.mq;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.spring.mq.config.AMQProducerConfig;
import com.example.spring.mq.config.AMQQueueSender;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes={AMQProducerConfig.class})
public class Producer {
	@Autowired
	private AMQQueueSender amqQueueSender;
	@Test
	public void test() {
		for(int i=1;i<10000;i++){
			try {
				amqQueueSender.send(i+"");
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}