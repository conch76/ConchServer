package com.conch.packet.request;

import com.conch.server.task.LoginTask;
import com.conch.server.task.ServerTask;

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
	@Override
	public ServerTask createTask() {
		return new LoginTask(this);
	}
}
