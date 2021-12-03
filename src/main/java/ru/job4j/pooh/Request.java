package ru.job4j.pooh;

public class Request {
	
	private final String httpRequestType;
	private final String poohMode;
	private final String sourceName;
	private final String param;
	
	public Request(String httpRequestType, String poohMode, String sourceName, String param) {
		this.httpRequestType = httpRequestType;
		this.poohMode = poohMode;
		this.sourceName = sourceName;
		this.param = param;
	}
	
	/**
	 * request - класс, служит для парсинга входящего запроса.
	 * <p>
	 * httpRequestType - GET или POST. Он указывает на тип запроса.
	 * <p>
	 * poohMode - указывает на режим работы: queue или topic.
	 * <p>
	 * sourceName - имя очереди или топика.
	 * <p>
	 * param - содержимое запроса.
	 */
	
	public static Request of(String content) {
		String[] splitReq = content.split(System.lineSeparator());
		String[] reqData = splitReq[0].split("/");
		String param = reqData[3].split(" ")[0].trim();
		if (reqData.length == 4) {
			if (reqData[0].trim().equals("POST")) {
				param = splitReq[7];
			} else {
				param = "";
			}
		}
		return new Request(reqData[0].trim(), reqData[1].trim(), reqData[2].split(" ")[0].trim(), param);
	}
	
	public String httpRequestType() {
		return httpRequestType;
	}
	
	public String getPoohMode() {
		return poohMode;
	}
	
	public String getSourceName() {
		return sourceName;
	}
	
	public String getParam() {
		return param;
	}
}