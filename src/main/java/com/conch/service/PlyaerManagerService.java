package com.conch.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.conch.player.PlayerSession;

@Service
public class PlyaerManagerService {

	private ConcurrentHashMap<Long, PlayerSession> playerSession = new ConcurrentHashMap<Long, PlayerSession>();
	
	public boolean addPlayerSession(PlayerSession session) {
		if (playerAlreadyExist(session)) {
			return false;
		}
		playerSession.put(session.getPlayerNumber(), session);
		return true;
	}
	
	public void removePlayerSession(long playerNumber) {
		playerSession.remove(playerNumber);
	}

	private boolean playerAlreadyExist(PlayerSession session) {
		return playerSession.containsKey(session.getPlayerNumber());
	}
	
}
