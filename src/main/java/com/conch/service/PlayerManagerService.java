package com.conch.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conch.domain.Player;
import com.conch.handler.exception.ServerException;
import com.conch.packet.request.LoginPacket;
import com.conch.player.PlayerSession;

@Service
public class PlayerManagerService implements SessionManagerService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ConcurrentMap<Integer, PlayerSession> playerSession = new ConcurrentHashMap<Integer, PlayerSession>();
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private ClientSessionNumberService sessionNumberService;
	
	public void disconnect(ChannelHandlerContext ctx) {
		playerSession.entrySet().stream().parallel().filter(e->e.getValue().getCtx() == ctx).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	@Override
	public int createNewSession(ChannelHandlerContext ctx, LoginPacket loginPacket) {
		// verify id/pwd first
		String userId = loginPacket.getUserId();
		Player player = playerService.getPlayerByUserId(userId);
		if (player == null || !loginPacket.getUserPassword().equals(player.getPassword())) {
			throw new ServerException("invalid account id or password");
		}
		
		PlayerSession existingSession =  getPlayerSessionByUserId(userId);
		if (existingSession != null) {
			this.removeSession(existingSession.getSessionNumber());
			existingSession.disconnectSession();
		}
		// create new!
		return createNewPlayerSession(ctx, player);
	}


	@Override
	public void removeSession(int sessionNumber) {
		logger.info("Removing Session {} from server!", sessionNumber);
		playerSession.remove(sessionNumber);
		// TODO : broad cast packet to existing
	}

	private int createNewSessionNumber() {
		return sessionNumberService.getFirstAvailableSessionNumber();
	}
	
	private int createNewPlayerSession(ChannelHandlerContext ctx, Player player) {
		int sessionNumber = createNewSessionNumber();
		PlayerSession session = new PlayerSession(sessionNumber, player, ctx);
		playerSession.put(sessionNumber, session);
		return sessionNumber;
	}
	
	private PlayerSession getPlayerSessionByUserId(String userId) {
		Optional<Entry<Integer, PlayerSession>> entry =  playerSession.entrySet().stream()
				.filter(e -> e.getValue().getPlayer().getUserId().equals(userId))
				.findFirst();
		return entry.isPresent() ? entry.get().getValue() : null;
	}
}
