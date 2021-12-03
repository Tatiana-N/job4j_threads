package ru.job4j.pooh;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
	
	ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		ConcurrentLinkedQueue<String> strings = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<String> result = topics.putIfAbsent(request.getSourceName(), strings);
		if (request.getParam() != null) {
			strings.add(request.getParam());
		}
		if ("POST".equals(request.httpRequestType())) {
			Optional.ofNullable(result).ifPresent(res -> res.add(request.getParam()));
		}
		return result != null && !result.isEmpty()
				? new Response(result.poll(), "200")
				: new Response("", result != null ? "203" : "200");
	}
}
