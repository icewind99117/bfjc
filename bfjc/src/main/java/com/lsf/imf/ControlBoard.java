package com.lsf.imf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lsf.imf.mq.ACDMDSClient;

@Component
public class ControlBoard {
	/*
	@Autowired
	private ACDMDSClient mqDsClient;
	
	private static ControlBoard controlBoard;
	
	@PostConstruct
	public void init() {
		controlBoard = this;
		controlBoard.mqDsClient = this.mqDsClient;
	}
	public static ACDMDSClient getDsClient() {
		return controlBoard.mqDsClient;
	}
	*/
	
	/*
	private static ACDMDSClient mqDsClient;
	static {
		mqDsClient=new ACDMDSClient();
	}
	
	public static ACDMDSClient getDsClient() {
		return mqDsClient;
	}
	*/
	
	private static ACDMDSClient mqDsClient;
	
	@Autowired(required = true)
	public void setACDMDSClient(ACDMDSClient client) {
		ControlBoard.mqDsClient = client;
	}
	
	public static ACDMDSClient getDsClient() {
		return mqDsClient;
	}
	
	
}
