package com.conch.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class PacketLengthDecodeHandler extends LengthFieldBasedFrameDecoder {

	public PacketLengthDecodeHandler(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
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
		// TODO : do something with the packet!
		return decoded;
	}
}
