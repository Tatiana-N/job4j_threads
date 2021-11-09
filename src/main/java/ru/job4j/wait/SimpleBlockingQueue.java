package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
	
	@GuardedBy("this")
	private final Queue<T> queue = new LinkedList<>();
	
	public synchronized void offer(T value) {
		queue.add(value);
		notifyAll();
	}
	
	public synchronized T poll() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return queue.poll();
	}
	
	public synchronized Queue<T> getQueue() {
		return queue;
	}
}
