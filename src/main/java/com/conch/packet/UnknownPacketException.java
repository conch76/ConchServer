package com.conch.packet;

public class UnknownPacketException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UnknownPacketException(ReflectiveOperationException e) {
		super(e);
	}


}
