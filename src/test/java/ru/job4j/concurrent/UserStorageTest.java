package ru.job4j.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.synchronize.User;
import ru.job4j.synchronize.UserStorage;

import java.util.Optional;

class UserStorageTest {
	
	@Test
	void add() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		storage.add(user1);
		storage.add(user2);
		Assertions.assertEquals(storage.getUsers().size(), 2);
		Assertions.assertEquals(storage.getUsers().get(1), user1);
		Assertions.assertEquals(storage.getUsers().get(2), user2);
	}
	
	@Test
	void transferNotFoundUser() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		storage.add(user1);
		storage.add(user2);
		storage.delete(user2);
		Assertions.assertFalse( storage.transfer(1, 2, 50));
	}
	
	@Test
	void update() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(1, 200);
		storage.add(user1);
		storage.update(user2);
		Assertions.assertEquals(storage.getUsers().size(), 1);
		Assertions.assertEquals(storage.getUsers().get(1), user2);
	}
	
	@Test
	void delete() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		storage.add(user1);
		storage.add(user2);
		storage.delete(user2);
		Assertions.assertEquals(storage.getUsers().size(), 1);
		Assertions.assertEquals(storage.getUsers().get(1), user1);
		Assertions.assertNull(storage.getUsers().get(2));
	}
	
	@Test
	void transfer() {
		UserStorage storage = new UserStorage();
		User user1 = new User(1, 100);
		User user2 = new User(2, 200);
		storage.add(user1);
		storage.add(user2);
		Assertions.assertTrue(storage.transfer(1, 2, 50));
		Assertions.assertEquals(storage.getUsers().get(1).getAmount(), 50);
		Assertions.assertEquals(storage.getUsers().get(2).getAmount(), 250);
	}
	
	
	@Test
	void addWithThread() throws InterruptedException {
		UserStorage storage = new UserStorage();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User(i, 100);
				storage.add(user);
			}
		});
		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User((i + 1) * 10000, 100);
				storage.add(user);
			}
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		Assertions.assertEquals(storage.getUsers().size(), 2000);
	}
	
	@Test
	void addAndDeleteWithThread() throws InterruptedException {
		UserStorage storage = new UserStorage();
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User(i, 100);
				storage.add(user);
			}
		});
		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				User user = new User(1000 + i, 100);
				storage.add(user);
			}
		});
		Thread thread3 = new Thread(() -> {
			for (int i = 0; i < 500; i++) {
				Optional<Integer> userId = storage.getUsers().keySet().stream().findAny();
				Assertions.assertTrue(userId.isPresent());
				User user = storage.getUsers().get(userId.get());
				Assertions.assertTrue(storage.delete(user));
			}
		});
		thread1.start();
		thread2.start();
		Thread.sleep(10);
		thread3.start();
		thread1.join();
		thread3.join();
		thread2.join();
		Assertions.assertEquals(storage.getUsers().size(), 1500);
	}
	
}