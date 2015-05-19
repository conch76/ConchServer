package com.conch.player;

import io.netty.channel.ChannelHandlerContext;

import com.conch.domain.Player;

public class PlayerSession {
	
	private long playerNumber;
	private Player player;
	private ChannelHandlerContext ctx;
	
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
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
}
