package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReqTest {
	
	@Test
	public void whenQueueModePostMethod() {
		String ls = System.lineSeparator();
		String content = "POST /queue/weather HTTP/1.1" + ls + "Host: localhost:9000" + ls + "User-Agent: curl/7.72.0" + ls + "Accept: */*" + ls + "Content-Length: 14" + ls + "Content-Type: application/x-www-form-urlencoded" + ls + "" + ls + "temperature=18" + ls;
		Request request = Request.of(content);
		assertEquals(request.httpRequestType(), "POST");
		assertEquals(request.getPoohMode(), "queue");
		assertEquals(request.getSourceName(), "weather");
		assertEquals(request.getParam(), "temperature=18");
	}
	
	@Test
	public void whenQueueModeGetMethod() {
		String ls = System.lineSeparator();
		String content = "GET /queue/weather HTTP/1.1" + ls + "Host: localhost:9000" + ls + "User-Agent: curl/7.72.0" + ls + "Accept: */*" + ls + ls + ls;
		Request request = Request.of(content);
		assertEquals(request.httpRequestType(), "GET");
		assertEquals(request.getPoohMode(), "queue");
		assertEquals(request.getSourceName(), "weather");
		assertEquals(request.getParam(), "");
	}
	
	@Test
	public void whenTopicModePostMethod() {
		String ls = System.lineSeparator();
		String content = "POST /topic/weather HTTP/1.1" + ls + "Host: localhost:9000" + ls + "User-Agent: curl/7.72.0" + ls + "Accept: */*" + ls + "Content-Length: 14" + ls + "Content-Type: application/x-www-form-urlencoded" + ls + "" + ls + "temperature=18" + ls;
		Request request = Request.of(content);
		assertEquals(request.httpRequestType(), "POST");
		assertEquals(request.getPoohMode(), "topic");
		assertEquals(request.getSourceName(), "weather");
		assertEquals(request.getParam(), "temperature=18");
	}
	
	@Test
	public void whenTopicModeGetMethod() {
		String ls = System.lineSeparator();
		String content = "GET /topic/weather/client407 HTTP/1.1" + ls + "Host: localhost:9000" + ls + "User-Agent: curl/7.72.0" + ls + "Accept: */*" + ls + ls + ls;
		Request request = Request.of(content);
		assertEquals(request.httpRequestType(), "GET");
		assertEquals(request.getPoohMode(), "topic");
		assertEquals(request.getSourceName(), "weather");
		assertEquals(request.getParam(), "client407");
	}
}