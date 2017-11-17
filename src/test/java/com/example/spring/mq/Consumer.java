package com.example.spring.mq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration(locations={"classpath:spring-consumer2.xml"})
@ContextConfiguration(classes = {AMQConsumerConfig.class})
public class Consumer {
	@Test
	public void test() {
		try {
			Thread.sleep(100*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}