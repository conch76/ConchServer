package com.conch.packet.request;

import io.netty.channel.ChannelHandlerContext;

import com.conch.server.task.LogoutTask;
import com.conch.server.task.ServerTask;


public class LogoutPacket extends BaseRequestPacket {

	@Override
	public ServerTask createTask(ChannelHandlerContext ctx) {
		return new LogoutTask();
	}

}
