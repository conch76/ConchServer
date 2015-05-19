package com.conch.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userNumber;
	
	@Column(unique = true)
	private String userId;
	@Column
	private String password;
	
	@OneToMany(mappedBy="player", fetch=FetchType.EAGER)
	private List<Champion> champion;
	
	public Player() {
		champion = new ArrayList<Champion>();
	}
	
	public long getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(long userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Champion> getChampion() {
		return champion;
	}
	public void setChampion(List<Champion> champion) {
		this.champion = champion;
	}
	
	public void addChampion(Champion champ) {
		champion.add(champ);
	}
}
