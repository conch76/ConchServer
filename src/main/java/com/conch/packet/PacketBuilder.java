package com.conch.packet;

import com.conch.packet.server.BaseServerPacket;

public class PacketBuilder {
	
	public static BaseServerPacket buildServerPacket(byte packetByte) {
		BaseServerPacket packet = null;
		try {
			ServerPacketType type = ServerPacketType.values()[packetByte];
			packet = (BaseServerPacket) type.getPacketClass().newInstance();
			packet.setPacketType(type);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new UnknownPacketException(e);
		}
		return packet;
	}
}
