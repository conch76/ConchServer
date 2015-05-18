package com.conch;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.conch.dao.ChampionDao;
import com.conch.dao.PlayerDao;
import com.conch.domain.Champion;
import com.conch.domain.ChampionType;
import com.conch.domain.Player;

@Component
public class MockDataService {

	@Autowired
	private PlayerDao playerDao;
	
	@Autowired
	private ChampionDao championDao;
	
	@Transactional
	public void createTestData() {
		
		Player player = new Player();
		player.setPassword("1111");
		player.setUserId("test");
		
		Champion champion = new Champion();
		champion.setBaseDexterity(10);
		champion.setBaseEndurance(10);
		champion.setBaseStrength(15);
		champion.setChampionType(ChampionType.Leesin);
		champion.setLevel(1);
		
		champion.setPlayer(player);
		player.addChampion(champion);
		
		playerDao.save(player);
		championDao.save(champion);
	}
}
