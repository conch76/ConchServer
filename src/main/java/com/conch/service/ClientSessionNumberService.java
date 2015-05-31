package com.conch.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * TODO : this needs to be refactored so that sessionCount number is reused.
 * @author schoi
 *
 */
@Service
public class ClientSessionNumberService {

	private AtomicInteger sessionCount;
	
	@PostConstruct
	public void init() {
		sessionCount = new AtomicInteger(0);
	}
	
	public int getFirstAvailableSessionNumber() {
		return sessionCount.getAndIncrement();
	}

	//TODO : session 번호를 release해야 한다!
	public void releaseSessionNumber() {
	}

}
