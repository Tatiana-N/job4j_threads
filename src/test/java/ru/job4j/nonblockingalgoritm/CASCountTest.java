package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CASCountTest {
	
	@Test
	void increment() throws InterruptedException {
		CASCount count = new CASCount();
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					count.increment();
				}
			}).start();
		}
		Thread.sleep(2000);
		Assertions.assertEquals(count.get(), 100000);
	}
}