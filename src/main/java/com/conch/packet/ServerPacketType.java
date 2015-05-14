package com.conch.packet;

import com.conch.packet.server.LoginPacket;
import com.conch.packet.server.LogoutPacket;

public enum ServerPacketType {
	
	LOGIN(LoginPacket.class),
	LOGOUT(LogoutPacket.class);
	
	private Class<?> clazz;
	
	private ServerPacketType(Class<?> type) {
		this.clazz = type;
	}
	
	public Class<?> getPacketClass() {
		return this.clazz;
	}
}
