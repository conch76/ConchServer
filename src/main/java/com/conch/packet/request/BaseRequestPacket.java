package com.conch.packet.request;

import com.conch.packet.RequestPacketType;
import com.conch.server.task.ServerTask;

	
public abstract class BaseRequestPacket {
	// trasient로 해야  protubuf가 serialize 안함
	private transient RequestPacketType packetType;
	

	public RequestPacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(RequestPacketType packetType) {
		this.packetType = packetType;
	}
	
	public abstract ServerTask createTask();
}
