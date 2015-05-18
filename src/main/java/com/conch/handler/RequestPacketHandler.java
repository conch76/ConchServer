package com.conch.handler;

import com.conch.packet.request.BaseRequestPacket;
import com.conch.packet.request.LoginPacket;
import com.conch.server.task.ServerTask;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RequestPacketHandler extends  ChannelHandlerAdapter {
	
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	      super.channelRead(ctx, msg);
	      if (msg instanceof BaseRequestPacket) {
	    	  ServerTask task = ((BaseRequestPacket) msg).createTask();
	    	  // TODO : put this into queue for async processing
	      }
	    }

}
