package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;


public class ParallelSearching extends RecursiveTask<Integer> {

  private final int start;
  private final int finish;
  private final Object[] objects;
  private final Object o;

  public ParallelSearching(Object[] objects, int start, int finish, Object o) {
    if (o == null) {
      throw new IllegalArgumentException("не можем искать null");
    }
    this.start = start;
    this.finish = finish;
    this.objects = objects;
    this.o = o;
  }

  public ParallelSearching(Object[] objects, Object o) {
    this(objects, 0, objects.length - 1, o);
  }

  @Override
  protected Integer compute() {
    if (finish - start <= 10) {
      return smileMassiveSearch(start, finish);
    }
    int mid = (start + finish) / 2;
    ParallelSearching parallelSearchingLeft = new ParallelSearching(objects, start, mid, o);
    ParallelSearching parallelSearchingRight = new ParallelSearching(objects, mid + 1, finish, o);
    parallelSearchingLeft.fork();
    parallelSearchingRight.fork();
    Integer indexLeft = parallelSearchingLeft.join();
    Integer indexRight = parallelSearchingRight.join();
    return indexLeft == -1 ? indexRight : indexLeft;
  }

  private Integer smileMassiveSearch(int start, int finish) {
    for (int i = start; i <= finish; i++) {
      if (o.equals(objects[i])) {
        return i;
      }
    }
    return -1;
  }
}
