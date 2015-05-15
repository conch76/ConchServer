package com.conch;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.conch.handler.PacketLengthDecodeHandler;
import com.conch.handler.RequestPacketHandler;
import com.conch.handler.ResponsePacketHandler;

@Component(value="tcpServerBootstrap")
public class TcpServerBootstrap {
	
	@Value("${conch.server.port}")
	private int port;
	
	protected ServerBootstrap server;
	protected Channel serverFuture;

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
									ch.pipeline().addLast(new PacketLengthDecodeHandler(1024, 0, 2, 0, 2)); // inbound
									ch.pipeline().addLast(new ResponsePacketHandler()); // outbound
									ch.pipeline().addLast(new RequestPacketHandler()); // inbound
								}
							}).option(ChannelOption.SO_BACKLOG, 128) 
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			serverFuture = server.bind(port).sync().channel(); 
			System.out.println("STARTED SERVER WAITING FOR CONNECTION");
			
			// Wait until the connection is closed.
			serverFuture.closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
