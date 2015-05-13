package com.conch;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TestClientHandler extends ChannelHandlerAdapter {
	 private final ByteBuf firstMessage;
	 private final ByteBuf secondMessage;
	 
	 /**
	     * Creates a client-side handler.
	     */
	    public TestClientHandler() {
	    	byte[] bytes = ByteBuffer.allocate(4).putShort((short) 1).array();
	    	bytes[2] = 1; // type
	    	bytes[3] = 2; // type
	    	
	        firstMessage = Unpooled.copiedBuffer(bytes);
	        secondMessage = Unpooled.copiedBuffer(bytes);
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) {
	        ctx.writeAndFlush(firstMessage);
	        ctx.writeAndFlush(secondMessage);
	    }

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
	        ctx.write(msg);
	    }

	    @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) {
	       ctx.flush();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        // Close the connection when an exception is raised.
	        cause.printStackTrace();
	        ctx.close();
	    }
}
