package com.conch.packet;

import static org.junit.Assert.assertTrue;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import org.junit.Test;

import com.conch.packet.request.LoginPacket;

public class ProtobufTest {

	private static String TEST_USER_ID = "myBadassUser";
	private static String TEST_USER_PASSWORD = "myBadassUserpassword";
	
	@Test
	public void testRuntime() {
		LoginPacket packet = new LoginPacket();
		packet.setUserId(TEST_USER_ID);
		packet.setUserPassword(TEST_USER_PASSWORD);
		Schema<LoginPacket> schema = RuntimeSchema.getSchema(LoginPacket.class);
		LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
		byte [] bytes = ProtostuffIOUtil.toByteArray(packet, schema, buffer);
		
		LoginPacket deserialized = new LoginPacket();
		ProtostuffIOUtil.mergeFrom(bytes, deserialized, schema);
		assertTrue(deserialized.getUserId().equals(TEST_USER_ID));
		assertTrue(deserialized.getUserPassword().equals(TEST_USER_PASSWORD));
	}
}
