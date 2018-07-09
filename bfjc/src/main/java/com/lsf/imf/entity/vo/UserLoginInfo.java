package com.lsf.imf.entity.vo;

import java.sql.Timestamp;

public class UserLoginInfo {
	private String userId;
	private String loginName;		
	private String userName;
	private Timestamp loginTime;
	
	public UserLoginInfo(UserInfo user) {
		this.userId=user.getId();
		this.loginName=user.getLoginName();
		this.userName=user.getUserName();
		this.loginTime=new Timestamp(System.currentTimeMillis());
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
