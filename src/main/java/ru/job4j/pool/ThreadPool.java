package ru.job4j.pool;

import ru.job4j.wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
  private final List<Thread> threads = new LinkedList<>();
  private final SimpleBlockingQueue<Runnable> tasks;

  public ThreadPool() {
    int size = Runtime.getRuntime().availableProcessors();
    tasks = new SimpleBlockingQueue<>(size);
    for (int i = 0; i < size; i++) {
      threads.add(new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
          try {
            tasks.poll().run();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }));
    }
    threads.forEach(Thread::start);
  }

  public void work(Runnable runnable) {
    try {
      tasks.offer(runnable);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void shoutDown() {
    threads.forEach(Thread::interrupt);
  }

  public static void main(String[] args) throws InterruptedException {
    ThreadPool threadPool = new ThreadPool();
    threadPool.work(() -> System.out.println("task1"));
    threadPool.work(() -> System.out.println("task2"));
    Thread.sleep(2000);
    threadPool.work(() -> System.out.println("task3"));
    Thread.sleep(1000);
    threadPool.work(() -> System.out.println("task4"));
    threadPool.shoutDown();
  }
}
