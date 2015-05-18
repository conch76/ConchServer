package com.conch.server.processor;

import org.springframework.stereotype.Service;

import com.conch.server.task.ServerTask;
import com.conch.server.util.concurrent.GenericConsumer;

/**
 * Client Request Data를 handling하는 Consumer이다!
 * ServerTask를 execute하자!
 * Threadsafety를 유지 하자
 * @author schoi
 *
 */
@Service
public class RequestHandlerConsumer implements GenericConsumer<ServerTask> {

	@Override
	public void consume(ServerTask task) {
		task.processTask();
	}

}
