package ru.job4j.nonblockingalgoritm;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@NotThreadSafe
public class Stack<T> {
	private final AtomicReference<Node<T>> head = new AtomicReference<>();
	
	public void push(T value) {
		Node<T> temp = new Node<>(value);
		Node<T> ref;
		do {
			ref = head.get();
			temp.next = ref;
		} while (!head.compareAndSet(ref, temp));
	}
	
	public T poll() {
		Node<T> temp;
		Node<T> ref;
		do {
			ref = head.get();
			if (ref == null) {
				throw new IllegalStateException("Stack is empty");
			}
			temp = ref.next;
		} while (!head.compareAndSet(ref, temp));
		ref.next = null;
		return ref.value;
	}
	
	
	private static final class Node<T> {
		private final T value;
		
		private Node<T> next;
		
		public Node(final T value) {
			this.value = value;
		}
	}
}
