package com.conch.packet;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import com.conch.packet.request.BaseRequestPacket;

public class PacketBuilder {
	
	@Deprecated
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
	
	public static BaseRequestPacket buildRequestPacket(byte packetByte, byte [] data) {
		BaseRequestPacket packet = null;
		RequestPacketType type = RequestPacketType.values()[packetByte];
		packet = (BaseRequestPacket) deserialize(data, type.getPacketClass());
		packet.setPacketType(type);
		return packet;
	}
	
	private static <T> T deserialize(byte [] data, Class<T> clazz) {
		T result = null;
		try {
			result = clazz.newInstance();
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			ProtostuffIOUtil.mergeFrom(data, result, schema);
		} catch (InstantiationException | IllegalAccessException e) {
			// should not happen in production!!
			e.printStackTrace();
		}
		return result;
	}
}
