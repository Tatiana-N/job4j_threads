package ru.job4j.concurrent;

import java.io.*;

public class FileSaver {
	private final File file;
	
	public FileSaver(File file) {
		this.file = file;
	}
	
	public synchronized void saveContent(String content) throws IOException {
		try (BufferedWriter o = new BufferedWriter(new FileWriter(file))) {
			o.write(content);
		}
	}
}