package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueueServiceTest {
	
	@Test
	public void whenPostThenGetQueue() {
		QueueService queueService = new QueueService();
		String paramForPostMethod = "temperature=18";
		String statusSuccess = "200";
		String statusFail = "203";
		/* Добавляем данные в очередь weather. Режим queue */
		queueService.process(new Request("POST", "queue", "weather", paramForPostMethod));
		/* Забираем данные из очереди weather. Режим queue */
		Response result1 = queueService.process(new Request("GET", "queue", "weather", null));
		Response result2 = queueService.process(new Request("GET", "queue", "weather", null));
		assertEquals(result1.text(), paramForPostMethod);
		assertEquals(result1.status(), statusSuccess);
		assertEquals(result2.status(), statusFail);
		assertEquals(result2.text(), "");
	}
}