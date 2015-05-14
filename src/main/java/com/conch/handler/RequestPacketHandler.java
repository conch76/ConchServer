package com.conch.handler;

import com.conch.packet.request.BaseRequestPacket;
import com.conch.packet.request.LoginPacket;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RequestPacketHandler extends  ChannelHandlerAdapter {
	
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	      super.channelRead(ctx, msg);
	      if (msg instanceof BaseRequestPacket) {
	    	  // TODO : handle packet specific task! and remove multiple instanceof hell!
	    	  System.out.println(((BaseRequestPacket) msg).getPacketType());
	    	  if (msg instanceof LoginPacket) {
	    		  System.out.println(((LoginPacket) msg).getUserId());
	    		  System.out.println(((LoginPacket) msg).getUserPassword());
	    		  System.out.println("Writing msg in request handler");
	    		  ctx.write(msg);
	    	  }
	      }
	    }

}
