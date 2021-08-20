package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(500);
			while (!Thread.currentThread().isInterrupted()) {
				System.out.print("\r load: " + "\\");
				System.out.print("\r load: " + "|");
				System.out.print("\r load: " + "/");
				System.out.print("\r load: " + "-");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
