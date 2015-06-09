package com.conch.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.netty.channel.ChannelHandlerContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.conch.domain.Player;
import com.conch.handler.exception.ServerException;
import com.conch.packet.request.LoginPacket;

@RunWith(MockitoJUnitRunner.class)
public class PlayerManagerServiceTest {
	
	private static final String TEST_USER_ID = "TEST";

	@InjectMocks
	private PlayerManagerService service;
	
	@Mock private PlayerService playerService;
	@Mock private ClientSessionNumberService sessionNumberService;
	@Mock private ChannelHandlerContext newContext;
	@Mock private ChannelHandlerContext origianContext;
	
	@Test
	public void createNewSessionTest_wrongPassword() {
		Player player = createPlayer();
		LoginPacket packet = new LoginPacket();
		packet.setUserId("WRONG");
		packet.setUserPassword(TEST_USER_ID);
		
		when(playerService.getPlayerByUserId(TEST_USER_ID)).thenReturn(player);
		when(sessionNumberService.getFirstAvailableSessionNumber()).thenReturn(1);
		try {
			service.createNewSession(newContext, packet);
			fail("PASSWORD SHOULD BE WRONG");
		} catch (ServerException e) {
			assertTrue(e.getMessage().equals("invalid account id or password"));
		}
	}
	
	@Test
	public void createNewSessionTest_disconnectDuplicateSession() {
		Player player = createPlayer();
		LoginPacket packet = new LoginPacket();
		packet.setUserId(TEST_USER_ID);
		packet.setUserPassword(TEST_USER_ID);
		
		when(playerService.getPlayerByUserId(TEST_USER_ID)).thenReturn(player);
		when(sessionNumberService.getFirstAvailableSessionNumber()).thenReturn(1);
		
		int sessionNumber = service.createNewSession(origianContext, packet);
		assertTrue(sessionNumber == 1);
		
		when(sessionNumberService.getFirstAvailableSessionNumber()).thenReturn(2);

		int newSessionNumber = service.createNewSession(newContext, packet);
		assertTrue(newSessionNumber == 2);
		verify(origianContext).disconnect(); // verify duplicated session is disconnected normally
	}
	
	private Player createPlayer() {
		Player player = new Player();
		player.setUserId(TEST_USER_ID);
		player.setPassword(TEST_USER_ID);
		return player;
	}

}
