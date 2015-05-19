package com.conch.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conch.domain.Player;
import com.conch.player.PlayerSession;

@Service
public class PlayerManagerService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ConcurrentHashMap<Long, PlayerSession> playerSession = new ConcurrentHashMap<Long, PlayerSession>();
	
	@Autowired
	private PlayerService playerService;
	
	public void login(String userId, ChannelHandlerContext ctx) {
		
	}
	
	public void disconnect(ChannelHandlerContext ctx) {
		playerSession.entrySet().stream().parallel().filter(e->e.getValue().getCtx() == ctx).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	public PlayerSession addPlayerSession(String userId) {
		Player player = playerService.getPlayerByUserId(userId);
		if (player == null) {
			throw new RuntimeException("FUCK IT no user found");
		}
		if (playerSession.containsKey(player.getUserNumber())) {
			logger.debug("Already in session");
			// already exist in sesison
			// TODO : 세션 끊고.. 새로 만들고..
			return playerSession.get(player.getUserNumber());
		}
		logger.debug("Creating new player Session!!!");
		PlayerSession newSession = new PlayerSession();
		newSession.setPlayer(player);
		playerSession.put(player.getUserNumber(), newSession);
		return newSession;
	}
	
	public void removePlayerSession(long playerNumber) {
		playerSession.remove(playerNumber);
	}

	public boolean playerAlreadyLoggedIn(String userId) {
//		return playerSession.containsKey(session.getPlayerNumber());
		return false;
	}
	
}
