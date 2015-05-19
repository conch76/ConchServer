package com.conch.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.conch.ConchServerApplication;
import com.conch.packet.request.BaseRequestPacket;
import com.conch.server.processor.ServerTaskExecutorService;
import com.conch.server.task.ServerTask;

public class RequestPacketHandler extends  ChannelHandlerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// hack to get spring service
	 private ServerTaskExecutorService taskService = ConchServerApplication.getAppContext().getBean(ServerTaskExecutorService.class);

	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	      super.channelRead(ctx, msg);
	      if (msg instanceof BaseRequestPacket) {
	    	  ServerTask task = (ServerTask) ((BaseRequestPacket) msg).createTask(ctx);
	    	  taskService.submitTask(task);
	    	  logger.debug("Submitted Server Task to Consumer queue");
	      }
	    }

}
