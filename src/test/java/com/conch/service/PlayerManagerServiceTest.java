package com.conch.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import io.netty.channel.ChannelHandlerContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.conch.domain.Player;
import com.conch.handler.exception.ServerException;
import com.conch.packet.request.LoginPacket;

public class PlayerManagerServiceTest {
	
	private static final String TEST_USER_ID = "TEST";

	@InjectMocks
	private PlayerManagerService service;
	
	@Mock
	private PlayerService playerService;

	@Mock
	private ClientSessionNumberService sessionNumberService;
	
	@Mock
	private ChannelHandlerContext ctx;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void createNewSessionTest_wrongPassword() {
		Player player = createPlayer();
		LoginPacket packet = new LoginPacket();
		packet.setUserId("WRONG");
		packet.setUserPassword(TEST_USER_ID);
		
		when(playerService.getPlayerByUserId(TEST_USER_ID)).thenReturn(player);
		when(sessionNumberService.getFirstAvailableSessionNumber()).thenReturn(1);
		try {
			service.createNewSession(ctx, packet);
			fail("PASSWORD SHOULD BE WRONG");
		} catch (ServerException e) {
			assertTrue(e.getMessage().equals("invalid account id or password"));
		}
	}
	
	private Player createPlayer() {
		Player player = new Player();
		player.setUserId(TEST_USER_ID);
		player.setPassword(TEST_USER_ID);
		return player;
	}

}
