package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
	
	ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		Response response;
		ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
		map.put(request.getParam(), new ConcurrentLinkedQueue<>());
		ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribers = topics.putIfAbsent(request.getSourceName(), map);
		
		switch (request.httpRequestType()) {
			case "GET":
				ConcurrentLinkedQueue<String> queue = subscribers != null ? subscribers.putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>()) : null;
				if (queue == null) {
					response = new Response("", "200");
				} else if (queue.isEmpty()) {
					response = new Response("", "203");
				} else {
					response = new Response(queue.poll(), "200");
				}
				break;
			
			case "POST":
				if (subscribers != null) {
					subscribers.values().forEach(que -> que.add(request.getParam()));
					response = new Response("", "200");
				} else {
					response = new Response("", "203");
				}
				break;
			default:
				response = new Response("", "203");
		}
		return response;
	}
}
