package com.conch.packet.server;

import com.conch.packet.ServerPacketType;

public class BaseServerPacket {
	
	private ServerPacketType packetType;

	public ServerPacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(ServerPacketType packetType) {
		this.packetType = packetType;
	}
}
