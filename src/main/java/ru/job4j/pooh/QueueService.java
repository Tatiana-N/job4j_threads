package ru.job4j.pooh;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
	
	ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		String status = "203";
		ConcurrentLinkedQueue<String> strings = new ConcurrentLinkedQueue<>();
		if (request.getParam() != null) {
			strings.add(request.getParam());
		}
		switch (request.httpRequestType()) {
			case "POST":
				Optional.ofNullable(topics.putIfAbsent(request.getSourceName(), strings)).ifPresent(queue -> queue.add(request.getParam()));
				break;
			case "GET":
				ConcurrentLinkedQueue<String> result = topics.putIfAbsent(request.getSourceName(), strings);
				if (result != null && !result.isEmpty()) {
					status = "200";
				}
				return result != null ? new Response(result.isEmpty() ? "" : result.poll(), status) : new Response("", status);
			default:
				throw new IllegalArgumentException();
		}
		return new Response("", status);
	}
}
