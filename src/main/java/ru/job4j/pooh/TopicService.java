package ru.job4j.pooh;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
	/**
	 * топик                 -> подписчик         -> очередь
	 */
	ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();
	
	@Override
	public Response process(Request request) {
		
		ConcurrentLinkedQueue<String> strings = null;
		switch (request.httpRequestType()) {
			case "POST":
				ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribers = topics.get(request.getSourceName());
				Optional.ofNullable(subscribers).ifPresent(subscriber -> subscriber.values().forEach(queue -> queue.add(request.getParam())));
				/**
				 * добавили значение всем подписчикам если они существуют, если нет 203
				 */
				return new Response("", subscribers == null ? "203" : "200");
			case "GET":
				ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
				map.put(request.getParam(), new ConcurrentLinkedQueue<>());
				ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscriber = topics.putIfAbsent(request.getSourceName(), map);
				
				if (subscriber == null) {
					/**
					 * подписались на новый топик
					 */
					return new Response("", "200");
				}
				strings = subscriber.putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>());
				if (strings == null) {
					/**
					 подписались на существующий топик
					 */
					return new Response("", "200");
				}
				break;
			default:
				throw new IllegalArgumentException();
		}
		return strings.size() > 0 ? new Response(strings.poll(), "200") : new Response("", "203");
	}
}
