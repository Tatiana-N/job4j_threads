package ru.job4j.nonblockingalgoritm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StackTest {
	
	@Test
	public void when3PushThen3Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		Assertions.assertEquals(stack.poll(), 3);
		Assertions.assertEquals(stack.poll(), 2);
		Assertions.assertEquals(stack.poll(), 1);
	}
	
	@Test
	public void when1PushThen1Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		Assertions.assertEquals(stack.poll(), 1);
	}
	
	@Test
	public void when2PushThen2Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		Assertions.assertEquals(stack.poll(), 2);
		Assertions.assertEquals(stack.poll(), 1);
	}
}