package com.conch.packet;

import static org.junit.Assert.assertTrue;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import org.junit.Test;

import com.conch.packet.request.LoginPacket;

public class ProtobufTest {

	protected static String TEST_USER_ID = "myBadassUser";
	protected static String TEST_USER_PASSWORD = "myBadassUserpassword";
	
	protected Schema<LoginPacket> loginSchema = RuntimeSchema.getSchema(LoginPacket.class);
	
	@Test
	public void testRuntime() {
		byte [] serializedData = serializeLoginPacketForTest();
		
		LoginPacket deserialized = new LoginPacket();
		ProtostuffIOUtil.mergeFrom(serializedData, deserialized, loginSchema);
		assertTrue(deserialized.getUserId().equals(TEST_USER_ID));
		assertTrue(deserialized.getUserPassword().equals(TEST_USER_PASSWORD));
	}

	protected byte[] serializeLoginPacketForTest() {
		LoginPacket packet = new LoginPacket();
		packet.setUserId(TEST_USER_ID);
		packet.setUserPassword(TEST_USER_PASSWORD);
		LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
		return ProtostuffIOUtil.toByteArray(packet, loginSchema, buffer);
	}
	
}
