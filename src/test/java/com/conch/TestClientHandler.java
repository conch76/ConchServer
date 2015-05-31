package com.conch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import com.conch.packet.request.LoginPacket;

public class TestClientHandler extends ChannelHandlerAdapter {
	 
		int echoCount = 2;
		
	 	/**
	     * Creates a client-side handler.
	     */
	    public TestClientHandler() {
	    	System.out.println("CREATING NEW");
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
	    	sendLoginPacket(ctx);
	    }

		private void sendLoginPacket(ChannelHandlerContext ctx) {
			Schema<LoginPacket> loginSchema = RuntimeSchema.getSchema(LoginPacket.class);
	    	LoginPacket packet = new LoginPacket();
			packet.setUserId("test");
			packet.setUserPassword("testMe");
			LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
			byte [] data =  ProtostuffIOUtil.toByteArray(packet, loginSchema, buffer);
			
			ByteBuf lengthBytes = Unpooled.copyShort(data.length + 1);
			byte [] packetType = new byte[1];
			packetType[0] = 0; 
			ByteBuf typeByte = Unpooled.copiedBuffer(packetType);
			ByteBuf dataBytes = Unpooled.wrappedBuffer(data);
			ByteBuf packetBytes = Unpooled.copiedBuffer(lengthBytes, typeByte, dataBytes);
			ctx.writeAndFlush(packetBytes);
		}

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    	if (echoCount > 0) {
		    	echoCount--;
		        sendLoginPacket(ctx);
	    	}
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
