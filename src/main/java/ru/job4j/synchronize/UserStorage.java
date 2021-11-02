package ru.job4j.synchronize;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public final class UserStorage {
	@GuardedBy("this")
	private final Map<Integer, User> users = new HashMap<>();
	
	public synchronized boolean add(User user) {
		return users.putIfAbsent(user.getId(), user) == null;
	}
	
	public synchronized boolean update(User user) {
		return users.replace(user.getId(), user) != null;
	}
	
	public synchronized boolean delete(User user) {
		return users.remove(user.getId()) != null;
	}
	
	public synchronized boolean transfer(int fromId, int toId, int amount) {
		User userFrom = users.get(fromId);
		User userTo = users.get(toId);
		if (userFrom == null || userTo == null || userFrom.getAmount() - amount < 0) {
			return false;
		}
		User userFromNew = User.of(userFrom.getId(), userFrom.getAmount() - amount);
		User userToNew = User.of(userTo.getId(), userTo.getAmount() + amount);
		return update(userFromNew) && update(userToNew);
	}
	
	public synchronized Map<Integer, User> getUsers() {
		return users;
	}
}
