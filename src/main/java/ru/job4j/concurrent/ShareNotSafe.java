package ru.job4j.concurrent;

public class ShareNotSafe {
	public static void main(String[] args) throws InterruptedException {
		UserCache cache = new UserCache();
		User user = User.of("name");
		cache.add(user);
		Thread first = new Thread(() -> {
			user.setName("rename");
			cache.add(user);
		});
		first.start();
		first.join();
		System.out.println(cache.findById(1).getName());
		System.out.println(cache.findAll().size());
	}
}