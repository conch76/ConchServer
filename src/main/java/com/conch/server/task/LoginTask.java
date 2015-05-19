package com.conch.server.task;

import com.conch.ConchServerApplication;
import com.conch.packet.request.LoginPacket;
import com.conch.service.PlayerManagerService;

public class LoginTask implements ServerTask{

	private LoginPacket packet;
	
	public LoginTask(LoginPacket packet) {
		this.packet = packet;
	}
	
	@Override
	public void processTask() {
		PlayerManagerService playerManagerService = ConchServerApplication.getPlayerManagerService();
		playerManagerService.addPlayerSession(packet.getUserId());
		
	}

}
