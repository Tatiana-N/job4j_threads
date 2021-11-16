package ru.job4j.pool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchingTest {
	
	@Test
	void computeInteger1() {
		Object[] objects = new Object[503];
		for (int i = 0; i < 502; i++) {
			objects[i] = (i + 1);
		}
		ParallelSearching parallelSearching = new ParallelSearching(objects, 1);
		Assertions.assertEquals(parallelSearching.compute(), 0);
		parallelSearching = new ParallelSearching(objects, 2);
		Assertions.assertEquals(parallelSearching.compute(), 1);
		parallelSearching = new ParallelSearching(objects, 3);
		Assertions.assertEquals(parallelSearching.compute(), 2);
		parallelSearching = new ParallelSearching(objects, 4);
		Assertions.assertEquals(parallelSearching.compute(), 3);
		parallelSearching = new ParallelSearching(objects, 434);
		Assertions.assertEquals(parallelSearching.compute(), 433);
		parallelSearching = new ParallelSearching(objects, 251);
		Assertions.assertEquals(parallelSearching.compute(), 250);
		parallelSearching = new ParallelSearching(objects, 252);
		Assertions.assertEquals(parallelSearching.compute(), 251);
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ParallelSearching(objects, null));
	}
	
	@Test
	void computeInteger2() {
		Object[] objects = new Object[500];
		for (int i = 0; i < 500; i++) {
			objects[i] = (i + 1);
		}
		ParallelSearching parallelSearching = new ParallelSearching(objects, 1);
		Assertions.assertEquals(parallelSearching.compute(), 0);
		parallelSearching = new ParallelSearching(objects, 2);
		Assertions.assertEquals(parallelSearching.compute(), 1);
		parallelSearching = new ParallelSearching(objects, 3);
		Assertions.assertEquals(parallelSearching.compute(), 2);
		parallelSearching = new ParallelSearching(objects, 4);
		Assertions.assertEquals(parallelSearching.compute(), 3);
		parallelSearching = new ParallelSearching(objects, 5);
		Assertions.assertEquals(parallelSearching.compute(), 4);
		parallelSearching = new ParallelSearching(objects, 32);
		Assertions.assertEquals(parallelSearching.compute(), 31);
		parallelSearching = new ParallelSearching(objects, 250);
		Assertions.assertEquals(parallelSearching.compute(), 249);
		parallelSearching = new ParallelSearching(objects, 251);
		Assertions.assertEquals(parallelSearching.compute(), 250);
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ParallelSearching(objects, null));
	}
	
	@Test
	void computeString2() {
		Object[] objects = new Object[500];
		for (int i = 0; i < 500; i++) {
			objects[i] = (i + 1) + "";
		}
		ParallelSearching parallelSearching = new ParallelSearching(objects, "1");
		Assertions.assertEquals(parallelSearching.compute(), 0);
		parallelSearching = new ParallelSearching(objects, "2");
		Assertions.assertEquals(parallelSearching.compute(), 1);
		parallelSearching = new ParallelSearching(objects, "3");
		Assertions.assertEquals(parallelSearching.compute(), 2);
		parallelSearching = new ParallelSearching(objects, "4");
		Assertions.assertEquals(parallelSearching.compute(), 3);
		parallelSearching = new ParallelSearching(objects, "5");
		Assertions.assertEquals(parallelSearching.compute(), 4);
		parallelSearching = new ParallelSearching(objects, "65");
		Assertions.assertEquals(parallelSearching.compute(), 64);
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ParallelSearching(objects, null));
		
	}
}
