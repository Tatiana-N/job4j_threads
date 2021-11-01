package ru.job4j.concurrent;

import net.jcip.annotations.*;

@ThreadSafe
public class Count {
	
	@GuardedBy("this")
	private int value;
	
	public synchronized void increment() {
		value++;
	}
	
	public synchronized int get() {
		return value;
	}
}