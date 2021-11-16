package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;


public class ParallelSearching extends RecursiveTask<Integer> {
	
	int start;
	int finish;
	Object[] objects;
	Object o;
	
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
		if (finish <= 10) {
			return smileMassiveSearch();
		}
		if (start == finish) {
			if (o.equals(objects[start])) {
				return start;
			} else {
				return -1;
			}
		}
		int mid = (start + finish) / 2;
		ParallelSearching parallelSearching1 = new ParallelSearching(objects, start, mid, o);
		ParallelSearching parallelSearching2 = new ParallelSearching(objects, mid + 1, finish, o);
		parallelSearching1.fork();
		parallelSearching2.fork();
		Integer join1 = parallelSearching1.join();
		Integer join2 = parallelSearching2.join();
		return join1 == -1 ? join2 : join1;
	}
	
	private Integer smileMassiveSearch() {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}
}
