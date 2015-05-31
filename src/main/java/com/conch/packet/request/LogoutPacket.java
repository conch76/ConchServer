package com.conch.packet.request;

import io.netty.channel.ChannelHandlerContext;

import com.conch.server.task.LogoutTask;
import com.conch.server.task.ServerTask;
import com.conch.service.SessionManagerService;


public class LogoutPacket extends BaseRequestPacket {

	@Override
	public ServerTask createTask(ChannelHandlerContext ctx, SessionManagerService sessionService) {
		return new LogoutTask();
	}

}
