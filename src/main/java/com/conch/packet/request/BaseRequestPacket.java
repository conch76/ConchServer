package com.conch.packet.request;

import com.conch.packet.RequestPacketType;

public class BaseRequestPacket {
	
	private RequestPacketType packetType;

	public RequestPacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(RequestPacketType packetType) {
		this.packetType = packetType;
	}
}
