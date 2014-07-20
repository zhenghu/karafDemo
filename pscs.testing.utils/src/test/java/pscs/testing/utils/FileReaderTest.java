package pscs.testing.utils;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class FileReaderTest {

	@Test
	public void read_get_file_content() throws Exception {
		// Given
		final String expectedContent = "Some content\nStored in this file";

		// When
		String content = new FileReader("file.txt").read();

		// Then
		assertThat(content).isEqualTo(expectedContent);
	}
}
