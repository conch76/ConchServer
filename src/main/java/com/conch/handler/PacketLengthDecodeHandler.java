package com.conch.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.conch.packet.PacketBuilder;
import com.conch.packet.request.BaseRequestPacket;
import com.conch.service.ClientSessionNumberService;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PacketLengthDecodeHandler extends LengthFieldBasedFrameDecoder {
	
    @Autowired
    private ClientSessionNumberService clientSessionService;

    @Autowired
	public PacketLengthDecodeHandler(@Value("${handler.packet.max.frame}") int maxFrameLength, 
									 @Value("${handler.packet.order}") int lengthFieldOffset,
									 @Value("${handler.packet.length}") int lengthFieldLength,
									 @Value("${handler.packet.adjustment}") int lengthAdjustment,
									 @Value("${handler.packet.strip}") int initialBytesToStrip) {
		
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip);
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf decoded = (ByteBuf)super.decode(ctx, in);
		if (decoded == null) {
			return null;
		}
		
		// come back again when there's more packet
		if (decoded.readableBytes() < 1) {
			return null;
		}
		
		// read the packet type
		byte packetType = decoded.readByte();
		// get rest of data block
		byte[] data = new byte[decoded.readableBytes()];
		decoded.readBytes(data);
		BaseRequestPacket packet = PacketBuilder.buildRequestPacket(packetType, data);
		return packet;
	}
	
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	// TODO : should release client session
    }
}
