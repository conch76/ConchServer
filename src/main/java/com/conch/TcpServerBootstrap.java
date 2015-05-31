package com.conch;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.conch.handler.PacketLengthDecodeHandler;
import com.conch.handler.RequestPacketHandler;
import com.conch.handler.ResponsePacketHandler;

@Component(value="tcpServerBootstrap")
public class TcpServerBootstrap {
	
    private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${conch.server.port}")
	private int port;
	
	protected ServerBootstrap server;
	protected Channel serverFuture;
	
	@Autowired
	private RequestPacketHandler requestPacketHandler;
	@Autowired
	private ResponsePacketHandler responsePacketHandler;
	
	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	public void init() throws InterruptedException {
		new Thread(() -> {
			try {
				startServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@PreDestroy
	public void destroyServer() throws InterruptedException {
		serverFuture.close();
	}
	
	private void startServer() throws InterruptedException  {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			server = new ServerBootstrap(); 
			server.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) 
					.childHandler(new ChannelInitializer<SocketChannel>() { 
								@Override
								public void initChannel(SocketChannel ch)
										throws Exception {
									//2 byte legnth를 읽고, length 많큼의 패킷을 만들어 반환해준다. pipeline 위에서 부터 차례대로 전달됨
									ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO)); // inbound and outbound
									ch.pipeline().addLast(getPacketLengthHandler()); // inbound
									ch.pipeline().addLast(responsePacketHandler); // outbound
									ch.pipeline().addLast(requestPacketHandler); // inbound
								}
								// return new instance of packetLengthHandler, this is because
								// LengthFieldBasedFrameDecoder is not @Shareable.  Netty desgin contract
								private ChannelHandler getPacketLengthHandler() {
									return appContext.getBean(PacketLengthDecodeHandler.class);
								}
							}).option(ChannelOption.SO_BACKLOG, 128) 
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			serverFuture = server.bind(port).sync().channel(); 
			logger.debug("STARTED SERVER WAITING FOR CONNECTION");
			
			// Wait until the connection is closed.
			serverFuture.closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
