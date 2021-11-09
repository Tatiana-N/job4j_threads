package ru.job4j.wait;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {
	@Test
	void offer() throws InterruptedException {
		SimpleBlockingQueue<Double> simpleBlockingQueue = new SimpleBlockingQueue<>();
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			threadList.add(new Thread(() -> simpleBlockingQueue.offer(Math.random())));
		}
		threadList.forEach(Thread::start);
		
		for (Thread thread : threadList) {
			thread.join();
		}
		Assertions.assertEquals(10, simpleBlockingQueue.getQueue().size());
	}
	
	@Test
	void poll() throws InterruptedException {
		SimpleBlockingQueue<Double> simpleBlockingQueue = new SimpleBlockingQueue<>();
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			threadList.add(new Thread(() -> simpleBlockingQueue.offer(Math.random())));
			threadList.add(new Thread(() -> {
				try {
					simpleBlockingQueue.poll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}));
		}
		threadList.forEach(Thread::start);
		for (Thread thread : threadList) {
			thread.join();
		}
		Assertions.assertEquals(0, simpleBlockingQueue.getQueue().size());
	}
	
	@Test
	void test() throws InterruptedException {
		final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
		final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
		Thread producer = new Thread(() -> {
			IntStream.range(0, 10).forEach(queue::offer);
		});
		producer.start();
		Thread consumer = new Thread(() -> {
			while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
				try {
					buffer.add(queue.poll());
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		consumer.start();
		producer.join();
		consumer.interrupt();
		consumer.join();
		Assertions.assertEquals(buffer, Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		
	}
}