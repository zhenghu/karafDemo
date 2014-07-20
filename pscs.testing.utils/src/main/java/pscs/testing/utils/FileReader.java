package pscs.testing.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileReader {
	
	private String fileName;

	public FileReader(String fileName) {
		this.fileName = fileName;
	}
	
	public String read() {
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
		Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
		String wholeFile = scanner.useDelimiter("\\A").next();
		try {
			resourceAsStream.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format("Could not read file %s.", fileName), e);
		}
		scanner.close();
		return wholeFile;
	}

}
