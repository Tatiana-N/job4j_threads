package ru.job4j.wait;

public class ParallelSearch {
	
	public static void main(String[] args) throws InterruptedException {
		SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
		final Thread consumer = new Thread(
				() -> {
					while (!Thread.currentThread().isInterrupted()) {
						try {
							System.out.println(queue.poll());
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
				}
		);
		consumer.start();
	Thread producer = 	new Thread(
				() -> {
					for (int index = 0; index != 3; index++) {
						queue.offer(index);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		
		);
	producer.start();
	producer.join();
	consumer.interrupt();
	}
}
