package ru.job4j.concurrent;

public class ThreadStop {
	public static void main(String[] args) throws InterruptedException {
		Thread progress = new Thread(new ConsoleProgress());
		progress.start();
		Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
		progress.interrupt();
	}
}