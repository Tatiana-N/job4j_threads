package ru.job4j.concurrent;

import net.jcip.annotations.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CountTest {
	
	@Test
	void increment() {
		List<Thread> threads = new ArrayList<>();
		Count count = new Count();
		for (int i = 0; i < 100; i++) {
			threads.add(new Thread(() -> {
				for (int j = 0; j < 1000000; j++) {
					count.increment();
				}
			}));
		}
		threads.forEach(Thread::start);
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Assertions.assertEquals(count.get(), 100000000);
	}
}
