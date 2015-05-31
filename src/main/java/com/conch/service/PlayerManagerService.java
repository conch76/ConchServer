package com.conch.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conch.player.PlayerSession;

@Service
public class PlayerManagerService implements SessionManagerService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ConcurrentMap<Integer, PlayerSession> playerSession = new ConcurrentHashMap<Integer, PlayerSession>();
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private ClientSessionNumberService sessionNumberService;
	
	public void login(String userId, ChannelHandlerContext ctx) {
		
	}
	
	public void disconnect(ChannelHandlerContext ctx) {
		playerSession.entrySet().stream().parallel().filter(e->e.getValue().getCtx() == ctx).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
	
	//TODO : id/pwd 체크 및 중복 로그인 체크 등등
	@Override
	public int createNewSession() {
		PlayerSession session = new PlayerSession();
		int createNewSessionId = createNewSessionId();
		playerSession.put(createNewSessionId, session);
		return createNewSessionId; 
	}
	
//	public PlayerSession addPlayerSession(String userId) {
//		Player player = playerService.getPlayerByUserId(userId);
//		if (player == null) {
//			throw new RuntimeException("FUCK IT no user found");
//		}
//		if (playerSession.containsKey(player.getUserNumber())) {
//			logger.debug("Already in session");
//			// already exist in sesison
//			// TODO : 세션 끊고.. 새로 만들고..
//			return playerSession.get(player.getUserNumber());
//		}
//		logger.debug("Creating new player Session!!!");
//		PlayerSession newSession = new PlayerSession();
//		newSession.setPlayer(player);
//		playerSession.put(player.getUserNumber(), newSession);
//		return newSession;
//	}
	
	@Override
	public void removeSession(int sessionNumber) {
		logger.info("Removing Session {} from server!", sessionNumber);
		playerSession.remove(sessionNumber);
	}

	public boolean playerAlreadyLoggedIn(String userId) {
//		return playerSession.containsKey(session.getPlayerNumber());
		return false;
	}

	private int createNewSessionId() {
		return sessionNumberService.getFirstAvailableSessionNumber();
	}
	
}
