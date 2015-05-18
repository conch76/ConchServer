package com.conch.packet.request;

import com.conch.server.task.LogoutTask;
import com.conch.server.task.ServerTask;


public class LogoutPacket extends BaseRequestPacket {

	@Override
	public ServerTask createTask() {
		return new LogoutTask();
	}

}
