package com.conch.player;

import io.netty.channel.ChannelHandlerContext;

import com.conch.domain.Player;

public class PlayerSession {
	
	private int sessionNumber;
	private Player player;
	private ChannelHandlerContext ctx;
	
	public PlayerSession (int sessionNumber, Player player, ChannelHandlerContext ctx) {
		this.sessionNumber = sessionNumber;
		this.player = player;
		this.ctx = ctx;
	}
	
	public void disconnectSession() {
		ctx.disconnect();
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

	public int getSessionNumber() {
		return sessionNumber;
	}

	public void setSessionNumber(int sessionNumber) {
		this.sessionNumber = sessionNumber;
	}
}
