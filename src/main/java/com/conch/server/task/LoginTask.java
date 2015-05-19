package com.conch.server.task;

import io.netty.channel.ChannelHandlerContext;

import com.conch.ConchServerApplication;
import com.conch.packet.request.LoginPacket;
import com.conch.service.PlayerManagerService;

public class LoginTask implements ServerTask{

	private LoginPacket packet;
	private ChannelHandlerContext ctx;
	
	public LoginTask(LoginPacket packet, ChannelHandlerContext ctx) {
		this.packet = packet;
		this.ctx = ctx;
	}
	
	@Override
	public void processTask() {
		PlayerManagerService playerManagerService = ConchServerApplication.getPlayerManagerService();
		playerManagerService.login(packet.getUserId(), ctx);
	}

}
