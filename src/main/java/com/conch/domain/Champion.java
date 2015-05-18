package com.conch.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Champion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int championId;
	@Enumerated(EnumType.STRING)
	private ChampionType championType;
	private int level;
	private int baseStrength;
	private int baseDexterity;
	private int baseEndurance;
	@ManyToOne
	private Player player;
	
	public int getChampionId() {
		return championId;
	}
	public void setChampionId(int championId) {
		this.championId = championId;
	}
	public ChampionType getChampionType() {
		return championType;
	}
	public void setChampionType(ChampionType championType) {
		this.championType = championType;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getBaseStrength() {
		return baseStrength;
	}
	public void setBaseStrength(int baseStrength) {
		this.baseStrength = baseStrength;
	}
	public int getBaseDexterity() {
		return baseDexterity;
	}
	public void setBaseDexterity(int baseDexterity) {
		this.baseDexterity = baseDexterity;
	}
	public int getBaseEndurance() {
		return baseEndurance;
	}
	public void setBaseEndurance(int baseEndurance) {
		this.baseEndurance = baseEndurance;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
