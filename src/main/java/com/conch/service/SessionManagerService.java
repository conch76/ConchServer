package com.conch.service;

import io.netty.channel.ChannelHandlerContext;

import com.conch.packet.request.LoginPacket;


// Marker Interface
public interface SessionManagerService {
	
	public int createNewSession(ChannelHandlerContext ctx, LoginPacket loginPacket);
	public void removeSession(int sessionNumber);
}
