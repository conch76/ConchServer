package com.conch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conch.dao.PlayerDao;
import com.conch.domain.Player;

@Service
public class PlayerService {

	@Autowired
	private PlayerDao playerDao;
	
	public Player getPlayerByUserId(String userId) {
		return playerDao.findByuserId(userId);
	}
}
