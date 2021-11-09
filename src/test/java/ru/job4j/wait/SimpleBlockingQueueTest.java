package ru.job4j.wait;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}