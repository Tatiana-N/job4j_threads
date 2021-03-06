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
		SimpleBlockingQueue<Double> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			threadList.add(new Thread(() -> {
				try {
					simpleBlockingQueue.offer(Math.random());
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}));
		}
		threadList.forEach(Thread::start);
		
		for (Thread thread : threadList) {
			thread.join();
		}
	}
	
	@Test
	void poll() throws InterruptedException {
		SimpleBlockingQueue<Double> simpleBlockingQueue = new SimpleBlockingQueue<>(1000);
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			threadList.add(new Thread(() -> {
				try {
					simpleBlockingQueue.offer(Math.random());
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}));
			threadList.add(new Thread(() -> {
				try {
					simpleBlockingQueue.poll();
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}));
		}
		threadList.forEach(Thread::start);
		for (Thread thread : threadList) {
			thread.join();
		}
	}
	
	@Test
	void test() throws InterruptedException {
		final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
		final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			list.add(i);
		}
		Thread producer = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				try {
					queue.offer(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		producer.start();
		Thread consumer = new Thread(() -> {
			while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
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
		Assertions.assertEquals(buffer, list);
		
	}
}