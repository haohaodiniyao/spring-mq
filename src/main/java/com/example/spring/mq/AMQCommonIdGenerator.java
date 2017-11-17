package com.example.spring.mq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AMQCommonIdGenerator {
	public static String genClientId(){
		String host = null;
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host+"#"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
