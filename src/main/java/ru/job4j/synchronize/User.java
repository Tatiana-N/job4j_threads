package ru.job4j.synchronize;

import java.util.Objects;

public class User {
	private int id;
	private int amount;
	
	public User(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	private User() {
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return getId() == user.getId() && getAmount() == user.getAmount();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
	
	public static User of(int id, int amount) {
		User user = new User();
		user.id = id;
		user.amount = amount;
		return user;
	}
	
	public int getId() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}
}