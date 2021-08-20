package ru.job4j.concurrent;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;

public class Wget implements Runnable {
	private final String url;
	private final int speed;
	
	public Wget(String url, int speed) {
		this.url = url;
		this.speed = speed;
	}
	
	@Override
	public void run() {
		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		     FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			while (true) {
				long timeStart = System.currentTimeMillis();
				bytesRead = in.read(dataBuffer, 0, 1024);
				if (bytesRead == -1){
					break;
				}
				fileOutputStream.write(dataBuffer, 0, bytesRead);
				long time = System.currentTimeMillis() - timeStart;
				if(time < speed)
				Thread.sleep(speed - time);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		String url = args[0];
		int speed = Integer.parseInt(args[1]);
		Thread wget = new Thread(new Wget(url, speed));
		wget.start();
		wget.join();
	}
}
