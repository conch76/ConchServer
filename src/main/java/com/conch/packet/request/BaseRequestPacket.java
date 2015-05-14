package com.conch.packet.request;

import com.conch.packet.RequestPacketType;

public class BaseRequestPacket {
	
	// trasient로 해야  protubuf가 serialize 안함
	private transient RequestPacketType packetType;

	public RequestPacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(RequestPacketType packetType) {
		this.packetType = packetType;
	}
}
