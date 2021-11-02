package ru.job4j.synchronize;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
	@GuardedBy("this")
	Map<Integer, User> users = new HashMap<>();
	
	public synchronized boolean add(User user) {
		User tempUser = User.of(user.getId(), user.getAmount());
		return users.putIfAbsent(tempUser.getId(), tempUser) != null;
	}
	
	public synchronized boolean update(User user) {
		User tempUser = User.of(user.getId(), user.getAmount());
		return users.replace(tempUser.getId(), tempUser) != null;
	}
	
	public synchronized boolean delete(User user) {
		User tempUser = User.of(user.getId(), user.getAmount());
		return users.remove(tempUser.getId()) != null;
	}
	
	public synchronized boolean transfer(int fromId, int toId, int amount) {
		User userFrom = users.get(fromId);
		User userTo = users.get(toId);
		if (userFrom == null || userTo == null) {
			throw new IllegalArgumentException("Not found users");
		}
		if (userFrom.getAmount() - amount < 0) {
			throw new IllegalArgumentException("Not Enough money");
		}
		User userFromNew = User.of(userFrom.getId(), userFrom.getAmount() - amount);
		User userToNew = User.of(userTo.getId(), userTo.getAmount() + amount);
		return update(userFromNew) && update(userToNew);
	}
	
	public synchronized Map<Integer, User> getUsers() {
		return users;
	}
}
