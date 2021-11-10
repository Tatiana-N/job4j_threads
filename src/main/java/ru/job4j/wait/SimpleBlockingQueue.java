package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
	private int size = 0;
	@GuardedBy("this")
	private final Queue<T> queue = new LinkedList<>();
	
	public synchronized void offer(T value) {
		queue.add(value);
		size++;
		notifyAll();
	}
	
	public synchronized T poll() throws InterruptedException {
		while (size == 0) {
			wait();
			notifyAll();
		}
		size--;
		return queue.poll();
	}
	
	public synchronized boolean isEmpty() {
		return size == 0;
	}
}
