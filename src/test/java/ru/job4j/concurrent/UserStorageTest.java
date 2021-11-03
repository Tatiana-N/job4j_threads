package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.synchronize.User;
import ru.job4j.synchronize.UserStorage;


class UserStorageTest {
	
	@Test
	void add() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		Assertions.assertTrue(storage.add(user1));
		Assertions.assertTrue(storage.add(user2));
	}
	
	@Test
	void transferNotFoundUser() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		Assertions.assertTrue(storage.add(user1));
		Assertions.assertTrue(storage.add(user2));
		Assertions.assertTrue(storage.delete(user2));
	}
	
	@Test
	void update() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(1, 200);
		Assertions.assertTrue(storage.add(user1));
		Assertions.assertTrue(storage.update(user2));

	}
	
	@Test
	void delete() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		Assertions.assertTrue(storage.add(user1));
		Assertions.assertTrue(storage.add(user2));
		Assertions.assertTrue(storage.delete(user2));

	}
	
	@Test
	void transfer() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		Assertions.assertTrue(storage.add(user1));
		Assertions.assertTrue(storage.add(user2));
		Assertions.assertTrue(storage.transfer(1, 2, 50));
	}
	
	
	@Test
	void addWithThread() throws InterruptedException {
		UserStorage storage = new UserStorage();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User(i, 100);
				Assertions.assertTrue(storage.add(user));
			}
		});
		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User((i + 1) * 10000, 100);
				Assertions.assertTrue(storage.add(user));
			}
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}
}