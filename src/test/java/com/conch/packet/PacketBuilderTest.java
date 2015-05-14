package com.conch.packet;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.conch.packet.request.BaseRequestPacket;
import com.conch.packet.request.LoginPacket;
import com.conch.packet.request.LogoutPacket;

public class PacketBuilderTest {
	
	
	@Test
	public void testLoginPacket() {
		BaseRequestPacket login = PacketBuilder.buildServerPacket((byte)0);
		assertTrue(login instanceof LoginPacket);
		
		BaseRequestPacket logout = PacketBuilder.buildServerPacket((byte)1);
		assertTrue(logout instanceof LogoutPacket);
	}
}
