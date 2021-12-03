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
		
		Response response = result == null
				? new Response("", "200")
				: result.isEmpty() ? new Response("", "203")
				: new Response(result.poll(), "200");
		
		if ("POST".equals(request.httpRequestType())) {
			Optional.ofNullable(result).ifPresent(res -> res.add(request.getParam()));
			strings.add(request.getParam());
			response = new Response("", "200");
		}
		return response;
	}
}
