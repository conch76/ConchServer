package com.conch.packet;

import com.conch.packet.request.BaseRequestPacket;

public class PacketBuilder {
	
	public static BaseRequestPacket buildServerPacket(byte packetByte) {
		BaseRequestPacket packet = null;
		try {
			RequestPacketType type = RequestPacketType.values()[packetByte];
			packet = (BaseRequestPacket) type.getPacketClass().newInstance();
			packet.setPacketType(type);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new UnknownPacketException(e);
		}
		return packet;
	}
}
