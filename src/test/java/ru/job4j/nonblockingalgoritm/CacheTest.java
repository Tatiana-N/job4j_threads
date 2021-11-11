package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CacheTest {
	@Test
	public void failUpdate() {
		Cache cache = new Cache();
		Base base = new Base(1, 0);
		cache.add(base);
		
		Base user1 = new Base(1, -1);
		user1.setName("User 2");
		Assertions.assertThrows(OptimisticException.class, () -> cache.update(user1), "разные версии не обновляем");
	}
	
	@Test
	public void successUpdate() {
		Cache cache = new Cache();
		Base base = new Base(1, 0);
		cache.add(base);
		
		Base user1 = new Base(1, 0);
		user1.setName("User 1");
		Assertions.assertTrue(cache.update(user1));
		Base user2 = new Base(1, 1);
		user2.setName("User 1");
		Assertions.assertTrue(cache.update(user2));
	}
	
	@Test
	public void successDelete() {
		Cache cache = new Cache();
		Base base = new Base(1, 0);
		cache.add(base);
		Assertions.assertFalse(cache.add(base));
		cache.delete(base);
		Assertions.assertTrue(cache.add(base));
	}
}