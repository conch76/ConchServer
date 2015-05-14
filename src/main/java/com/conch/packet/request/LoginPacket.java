package com.conch.packet.request;


public class LoginPacket extends BaseRequestPacket {
	
	private String userId;
	private String userPassword;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
