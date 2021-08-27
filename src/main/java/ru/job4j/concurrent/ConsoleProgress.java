package ru.job4j.concurrent;

import java.util.Arrays;

public class ConsoleProgress implements Runnable {

	@Override
	public void run() {
		String [] strings = new String[]{"\\","|","/","-"};
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Thread.sleep(500);
				Arrays.stream(strings).forEach(string ->System.out.print("\r load: " + string));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread progress = new Thread(new ConsoleProgress());
		progress.start();
		Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
		progress.interrupt();
	}
}
