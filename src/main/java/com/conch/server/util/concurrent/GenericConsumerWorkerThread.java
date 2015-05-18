package com.conch.server.util.concurrent;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericConsumerWorkerThread<T> implements Runnable {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private BlockingQueue<T> queue;
	private GenericConsumer<T> consumer;
	
	public GenericConsumerWorkerThread(GenericConsumer<T> consumer, BlockingQueue<T> queue) {
		this.queue = queue;
		this.consumer = consumer;
	}
	
	@Override
	public void run() {
		while(true) {
			T value = null;
			try {
				value = queue.take();
				consumer.consume(value);
				logger.debug("Getting a task from consumer queue!");
			} catch (InterruptedException e) {
				// shit happened
				e.printStackTrace();
				break;
			}
		}
	}

}
