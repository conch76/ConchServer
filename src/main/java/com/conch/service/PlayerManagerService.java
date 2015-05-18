package com.conch.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.conch.domain.Player;
import com.conch.player.PlayerSession;

@Service
public class PlayerManagerService {

	private ConcurrentHashMap<Long, PlayerSession> playerSession = new ConcurrentHashMap<Long, PlayerSession>();
	
	private PlayerService playerService;
	
	public PlayerSession addPlayerSession(String userId) {
		Player player = playerService.getPlayerByUserId(userId);
		if (playerSession.containsKey(player.getUserNumber())) {
			// already exist in sesison
			// TODO : 
			return null;
		}
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
