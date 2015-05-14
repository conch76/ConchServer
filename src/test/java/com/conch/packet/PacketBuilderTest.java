package com.conch.packet;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.conch.packet.server.BaseServerPacket;
import com.conch.packet.server.LoginPacket;
import com.conch.packet.server.LogoutPacket;

public class PacketBuilderTest {
	
	
	@Test
	public void testLoginPacket() {
		BaseServerPacket login = PacketBuilder.buildServerPacket((byte)0);
		assertTrue(login instanceof LoginPacket);
		
		BaseServerPacket logout = PacketBuilder.buildServerPacket((byte)1);
		assertTrue(logout instanceof LogoutPacket);
	}
}
