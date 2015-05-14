package com.conch.packet;

import com.conch.packet.request.LoginPacket;
import com.conch.packet.request.LogoutPacket;

public enum RequestPacketType {
	
	LOGIN(LoginPacket.class),
	LOGOUT(LogoutPacket.class);
	
	private Class<?> clazz;
	
	private RequestPacketType(Class<?> type) {
		this.clazz = type;
	}
	
	public Class<?> getPacketClass() {
		return this.clazz;
	}
}
