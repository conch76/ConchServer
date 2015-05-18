package com.conch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conch.domain.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

	Player findByuserId(String userId);
}
