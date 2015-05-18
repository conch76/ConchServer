package com.conch.player;

import com.conch.domain.Player;

public class PlayerSession {
	
	private long playerNumber;
	private Player player;
	
	public long getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(long playerNumber) {
		this.playerNumber = playerNumber;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

}
