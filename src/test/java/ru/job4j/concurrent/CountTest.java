package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountTest {
	
	@Test
	void increment() throws InterruptedException {
		Count count = new Count();
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					count.increment();
				}
			}).start();
		}
		Thread.sleep(200);
		Assertions.assertEquals(count.get(), 10000);
	}
}
