/**
 * 
 */
package pscs.testing.intermed.simulator;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import pscs.testing.intermed.infogare.ObjectFactory;
import pscs.testing.intermed.infogare.Reponse;
import pscs.testing.intermed.infogare.Reponse.Message;

import com.sncf.pscs.testing.intermed.utils.XMLutils;

/**
 * @author ZHU12373
 *
 */
public class CorrespondanceInfoGenerator {
	
	XMLutils xmLutils;


	public CorrespondanceInfoGenerator() {
		xmLutils = new XMLutils();
	}

	/**
	 * Gets the info gare.
	 * @param fileName 
	 *
	 * @return the info gare
	 * @throws FileNotFoundException 
	 */
	public String getInfoGare(String codeGare, String fileName) {
						
		try {
			Reponse reponse = createReponse(codeGare, fileName);
			return xmLutils.marshal(reponse);
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} catch (JAXBException e) {
			return e.getMessage();
		} catch (FileNotFoundException e) {
			return e.getMessage();
		} 
	}

	/**
	 * Creates the reponse.
	 *
	 * @return the reponse
	 * @throws FileNotFoundException 
	 * @throws JAXBException 
	 */
	private Reponse createReponse(String codeGare, String fileExample) throws FileNotFoundException, JAXBException {
		
		@SuppressWarnings("static-access")
		Reponse reponse = (Reponse)xmLutils.unMarshal(fileExample, "pscs.testing.intermed.infogare", ObjectFactory.class.getClassLoader());
		
		Message message = reponse.getMessage();
		message.setGare(BigInteger.valueOf(Integer.parseInt((codeGare))));
		reponse.setMessage(message);
		
		return reponse;
	}
	

}
