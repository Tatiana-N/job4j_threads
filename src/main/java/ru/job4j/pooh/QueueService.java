package ru.job4j.pooh;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
	
	ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		Response response;
		ConcurrentLinkedQueue<String> strings = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<String> result = topics.putIfAbsent(request.getSourceName(), strings);
		if ("GET".equals(request.httpRequestType())) {
			if (result == null) {
				response = new Response("", "200");
			} else if (result.isEmpty()) {
				response = new Response("", "203");
			} else {
				response = new Response(result.poll(), "200");
			}
		} else if ("POST".equals(request.httpRequestType())) {
			Optional.ofNullable(result).ifPresent(res -> res.add(request.getParam()));
			strings.add(request.getParam());
			response = new Response("", "200");
		} else {
			response = new Response("", "203");
		}
		return response;
	}
}
