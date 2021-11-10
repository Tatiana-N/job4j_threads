package ru.job4j.nonblockingalgoritm;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
	private final AtomicReference<Integer> count = new AtomicReference<>();
	
	public void increment() {
		Integer oldValue;
		Integer newValue;
		do {
			oldValue = count.get();
			newValue = oldValue == null ? 1 : oldValue + 1;
			
		} while (!count.compareAndSet(oldValue, newValue));
	}
	
	public int get() {
		return count.get();
	}
}
