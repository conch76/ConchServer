package com.conch.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.conch.packet.request.BaseRequestPacket;
import com.conch.server.task.ServerTask;

public class RequestPacketHandler extends  ChannelHandlerAdapter {
	
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	      super.channelRead(ctx, msg);
	      if (msg instanceof BaseRequestPacket) {
	    	  ServerTask task = (ServerTask) ((BaseRequestPacket) msg).createTask();
	    	  // TODO : put this into queue for async processing
	      }
	    }

}
