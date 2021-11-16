package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
	private final int size;
	@GuardedBy("this")
	private final Queue<T> queue = new LinkedList<>();
	
	public SimpleBlockingQueue(int size) {
		this.size = size;
	}
	public SimpleBlockingQueue() {
		this.size = -1;
	}
	
	public synchronized void offer(T value) throws InterruptedException {
		while (queue.size() == size) {
				wait();
		}
		queue.add(value);
		notifyAll();
	}
	
	public synchronized T poll() throws InterruptedException {
		while (queue.size() == 0) {
			wait();
		}
		T poll = queue.poll();
		notifyAll();
		return poll;
	}
	
	public synchronized boolean isEmpty() {
		return queue.size() == 0;
	}
}
