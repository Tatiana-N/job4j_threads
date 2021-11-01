package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;


public class FileReader {
	private final File file;
	
	public FileReader(File file) {
		this.file = file;
	}
	
	public synchronized String getContent(Predicate<Integer> predicate) throws IOException {
		try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
			StringBuilder output = new StringBuilder();
			int data;
			byte[] bytesBuffer = new byte[1024];
			while ((data = i.read(bytesBuffer)) != -1) {
				for (int i1 = 0; i1 < data; i1++) {
					if (predicate.test(data)) {
						output.append((char) data);
					}
				}
			}
			return output.toString();
		}
	}
	
	public String getContent() throws IOException {
		return getContent(t -> true);
	}
	
	public String getContentWithoutUnicode() throws IOException {
		return getContent(t -> t < 0x80);
	}
}