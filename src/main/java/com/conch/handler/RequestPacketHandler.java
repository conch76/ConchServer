package com.conch.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conch.packet.request.BaseRequestPacket;
import com.conch.server.processor.ServerTaskExecutorService;
import com.conch.server.task.ServerTask;
import com.conch.service.SessionManagerService;

@Sharable
@Component
public class RequestPacketHandler extends  ChannelHandlerAdapter {
	
    public static AttributeKey<Integer> CHANNEL_KEY = AttributeKey.newInstance(AttributeKey.class.toGenericString());

	@Autowired
	private SessionManagerService sessionManagerService;
	@Autowired
	private ServerTaskExecutorService serverTaskService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	      super.channelRead(ctx, msg);
	      if (msg instanceof BaseRequestPacket) {
	    	  ServerTask task = (ServerTask) ((BaseRequestPacket) msg).createTask(ctx, sessionManagerService);
	    	  serverTaskService.submitTask(task);
	    	  logger.debug("Submitted Server Task to Consumer queue");
	    	  
//	    	  ctx.write(Unpooled.copiedBuffer("Ack", CharsetUtil.UTF_8));
//	    	  logger.debug("SENT BACK PACKET TO CLIENT");
	      }
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<Integer> attribute = ctx.channel().attr(CHANNEL_KEY);
		Integer sessionNumber = attribute.get();
		if (sessionNumber != null) {
			sessionManagerService.removeSession(sessionNumber);
			return;
		}
		logger.debug("Removing not logged in session from server");
    }
}
