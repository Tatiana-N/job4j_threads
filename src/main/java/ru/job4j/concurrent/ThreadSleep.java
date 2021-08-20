package ru.job4j.concurrent;

public class ThreadSleep {
	public static void main(String[] args) {
		Thread thread = new Thread(
				() -> {
					try {
						loading();
						System.out.println("Loaded.");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		);
		thread.start();
		System.out.println("Main");
	}
	
	private static void loading() throws InterruptedException {
		System.out.println("Start loading ... ");
		int index = 0;
		while (index<=100){
			Thread.sleep(1000);
			System.out.print("\rLoading : " + index++  + "%");
		}
		System.out.println();
	}
}