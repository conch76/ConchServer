package com.conch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conch.dao.PlayerDao;
import com.conch.domain.ChampionType;
import com.conch.domain.Player;

@Service
public class PlayerService {

	@Autowired
	private PlayerDao playerDao;
	
	public Player getPlayerByUserId(String userId) {
		return playerDao.findByUserId(userId);
	}
	
	public Player getPlayerWithChampion(ChampionType type) {
//		playerDao.findOne(new Predicate)
		return null;
	}
}
