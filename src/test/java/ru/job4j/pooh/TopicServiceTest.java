package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TopicServiceTest {
	String statusSuccess = "200";
	String statusFail = "203";
	
	@Test
	public void whenTopic() {
		TopicService topicService = new TopicService();
		String paramForPublisher = "temperature=18";
		String paramForSubscriber1 = "client407";
		String paramForSubscriber2 = "client6565";
		/* Режим topic. Подписываемся на топик weather. client407. */
		topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
		/* Режим topic. Добавляем данные в топик weather. */
		topicService.process(new Request("POST", "topic", "weather", paramForPublisher));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
		Response result1 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
        /* Режим topic.  Подписываемся на топик weather. client6565. */
		Response result2 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber2));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565.
        Очередь отсутствует, т.к. еще не был подписан - получит пустую строку */
		Response result3 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber2));
		assertEquals(result1.text(), paramForPublisher);
		assertEquals(result1.status(), statusSuccess);
		assertEquals(result2.text(), "");
		assertEquals(result2.status(), statusSuccess);
		assertEquals(result3.text(), "");
		assertEquals(result3.status(), statusFail);
		
	}
	
	@Test
	public void whenPostWithoutSubscriberTopic() {
		TopicService topicService = new TopicService();
		String paramForPublisher = "temperature=100";
		String paramForSubscriber1 = "client407";
		/* Режим topic. Добавляем данные в топик weather. */
		Response result1 = topicService.process(new Request("POST", "topic", "weather", paramForPublisher));
		/* Режим topic. Подписываемся на топик weather. client407. */
		Response result2 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
		Response result3 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
		assertEquals(result1.text(), "");
		assertEquals(result1.status(), statusFail);
		assertEquals(result2.text(), "");
		assertEquals(result2.status(), statusSuccess);
		assertEquals(result3.text(), "");
		assertEquals(result3.status(), statusFail);
	}
	
	@Test
	public void when2SubscribersTopic() {
		TopicService topicService = new TopicService();
		String paramForPublisher = "temperature=45";
		String paramForSubscriber1 = "client407";
		String paramForSubscriber2 = "client6565";
		/* Режим topic. Подписываемся на топик weather. client407. */
		Response processSub1 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
		/* Режим topic. Подписываемся на топик weather. client6565. */
		Response processSub2 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber2));
		/* Режим topic. Добавляем данные в топик weather. */
		Response processPublish = topicService.process(new Request("POST", "topic", "weather", paramForPublisher));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client407. */
		Response result1 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber1));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565. */
		Response result2 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber2));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather. Очередь client6565.
		 * там пусто */
		Response result3 = topicService.process(new Request("GET", "topic", "weather", paramForSubscriber2));
		/* Режим topic. Забираем данные из индивидуальной очереди в топике weather2. Очередь client6565.
		 * там пусто нет такого топика
		 * подписались */
		Response result4 = topicService.process(new Request("GET", "topic", "weather2", paramForSubscriber2));
		assertEquals(processSub1.status(), statusSuccess);
		assertEquals(processSub2.status(), statusSuccess);
		assertEquals(processPublish.status(), statusSuccess);
		assertEquals(result1.text(), paramForPublisher);
		assertEquals(result1.status(), statusSuccess);
		assertEquals(result2.text(), paramForPublisher);
		assertEquals(result2.status(), statusSuccess);
		assertEquals(result3.text(), "");
		assertEquals(result3.status(), statusFail);
		assertEquals(result4.text(), "");
		assertEquals(result4.status(), statusSuccess);
	}
}