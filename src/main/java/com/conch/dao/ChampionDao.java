package com.conch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conch.domain.Champion;

public interface ChampionDao extends JpaRepository<Champion, Integer> {

}
