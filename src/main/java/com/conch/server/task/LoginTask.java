package com.conch.server.task;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

import com.conch.handler.RequestPacketHandler;
import com.conch.packet.request.LoginPacket;
import com.conch.service.SessionManagerService;

public class LoginTask implements ServerTask{

	private LoginPacket packet;
	private ChannelHandlerContext ctx;
	private SessionManagerService sessionService;
	
	public LoginTask(LoginPacket packet, ChannelHandlerContext ctx, SessionManagerService sessionService) {
		this.packet = packet;
		this.ctx = ctx;
		this.sessionService=sessionService;
	}
	
	@Override
	public void processTask() {
		processLogin(ctx, packet);
	}
	
	private void processLogin(ChannelHandlerContext ctx, LoginPacket packet) {
		// store session in sessionManager and create playerSession
		int sessionNumber = sessionService.createNewSession();
		// store session number in attribute key in channel.. to handle disconnect
		Attribute<Integer> attribute = ctx.channel().attr(RequestPacketHandler.CHANNEL_KEY);
		attribute.set(sessionNumber);
	}
}
