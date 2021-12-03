package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
	
	ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		
		ConcurrentLinkedQueue<String> queue = null;
		ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
		map.put(request.getParam(), new ConcurrentLinkedQueue<>());
		ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribers = topics.putIfAbsent(request.getSourceName(), map);
		
		switch (request.httpRequestType()) {
			case "GET":
				queue = subscribers != null ? subscribers.putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>()) : null;
				break;
			case "POST":
				if (subscribers != null) {
					subscribers.values().forEach(que -> que.add(request.getParam()));
					break;
				}
			default:
				return new Response("", "203");
		}
		return queue != null && !queue.isEmpty()
				? new Response(queue.poll(), "200")
				: new Response("", queue != null ? "203" : "200");
	}
}
