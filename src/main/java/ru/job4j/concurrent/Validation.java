package ru.job4j.concurrent;

public class Validation {
	public static boolean check(String[] args) {
		try {
			Integer.parseInt(args[1]);
			return args.length == 3;
		} catch (Exception e) {
			return false;
		}
	}
}
