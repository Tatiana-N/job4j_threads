package ru.job4j.concurrent;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;

public class Wget implements Runnable {
	private final String url;
	private final int speed;
	private final String fileName;
	
	public Wget(String url, int speed, String fileName) {
		this.url = url;
		this.speed = speed;
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
		     FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
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
		try {
			Integer.parseInt(args[1]);
			if (args.length != 3) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("неправильные аргументы");
		}
			String url = args[0];
			int speed = Integer.parseInt(args[1]);
			String fileName = args[2];
			Thread wget = new Thread(new Wget(url, speed, fileName));
			wget.start();
			wget.join();
	}
}
