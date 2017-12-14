package com.example.spring.mq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath*:config/spring/iao/sdl2-iao.xml","classpath*:config/spring/iao/sdl-iao.xml"})
public class Hello {


	@Test
	public void test() {

	}
	
}