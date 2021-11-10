package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CacheMockTest {
	@Test
	public void failUpdate() {
		Cache cache = mock(Cache.class);
		Base base = new Base(1, 0);
		Base user1 = new Base(1, -1);
		when(cache.add(base)).thenReturn(true);
		when(cache.update(user1)).thenThrow(new OptimisticException("разные версии не обновляем"));
		cache.add(base);
		Assertions.assertThrows(OptimisticException.class, () -> cache.update(user1), "разные версии не обновляем");
	}
}