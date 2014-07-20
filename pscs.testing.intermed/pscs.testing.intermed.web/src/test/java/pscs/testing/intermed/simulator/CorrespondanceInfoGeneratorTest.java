package pscs.testing.intermed.simulator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

public class CorrespondanceInfoGeneratorTest {

	@Test
	public void getInfoGare_should_return_Reponse() throws SAXException, IOException {
		// Given
		final String codeGare = "87391003";
		final String fileName = "infoGare.xml";
		String expectedXML = convertFileToString(fileName);
		CorrespondanceInfoGenerator correspondanceSimulator = new CorrespondanceInfoGenerator();

		// When
		String result = correspondanceSimulator.getInfoGare(codeGare, expectedXML);

		// Then
		XMLUnit.setIgnoreWhitespace(true);
		XMLAssert.assertXMLEqual(expectedXML, result);
	}

	/**
	 * Convert file to string.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the string
	 */
	private String convertFileToString(String fileName) {
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
