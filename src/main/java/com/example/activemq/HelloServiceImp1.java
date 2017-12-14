package com.example.activemq;

public class HelloServiceImp1 implements HelloService {
	private Integer agentId;
	@Override
	public String sayHello(String msg) {
		return msg+"#"+agentId;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

}
