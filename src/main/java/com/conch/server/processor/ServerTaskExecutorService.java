package com.conch.server.processor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.conch.server.task.ServerTask;
import com.conch.server.util.concurrent.GenericConsumerWorkerThread;

@Service
public class ServerTaskExecutorService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${conch.server.consumer.thread.count}")
	private int consumerThreadCount;
	
	private BlockingQueue<ServerTask> queue;
	private ExecutorService executorService;
	@Autowired private RequestHandlerConsumer consumer;
	
	@PostConstruct
	public void init() {
		// initialize queue
		queue = new LinkedBlockingQueue<ServerTask>();
		executorService = Executors.newFixedThreadPool(consumerThreadCount);
		logger.debug("Initializing PriorityBlocking Queue...");
		logger.debug("Initializing ServerTask Consumer Thread with count = " + consumerThreadCount);
		startConsumerThread();
	}
	
	
	public void submitTask(ServerTask task) {
		try {
			queue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
			// queue is full and out of memory!!!  give up the server
			logger.error("Task Queue is full! shutting down service");
			// TODO : shutdown gracefully
		}
	}

	private void startConsumerThread() {
		for (int i = 0; i < consumerThreadCount; i++) {
			executorService.execute(new GenericConsumerWorkerThread<ServerTask>(consumer, queue));
			logger.debug("Initializing ServerTask Consumer Thread = " + i);
		}
	}
	
	
}
