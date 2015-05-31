package com.conch.packet.request;

import io.netty.channel.ChannelHandlerContext;

import com.conch.server.task.LoginTask;
import com.conch.server.task.ServerTask;
import com.conch.service.SessionManagerService;

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
	public ServerTask createTask(ChannelHandlerContext ctx, SessionManagerService sessionService) {
		return new LoginTask(this, ctx, sessionService);
	}
}
