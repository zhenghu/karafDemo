package com.sncf.pscs.testing.intermed.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLutils {

	/**
	 * Create a {@link Unmarshaller} for the given context and return it.
	 * 
	 * @param jaxbContextName the context name where to look to find jaxb classes
	 * @return the created {@link Unmarshaller}
	 * @throws JAXBException
	 */
	public static Unmarshaller createUnmarshaller(final String jaxbContextName, ClassLoader cl) throws JAXBException {
		final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbContextName, cl);
		return jaxbContext.createUnmarshaller();
	}

	/**
	 * @param message
	 * @param jaxbContextName
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return Object
	 * @throws JAXBException
	 */
	public static Object unMarshal(final String message, final String jaxbContextName, ClassLoader cl) throws JAXBException {
		Object obj = null;
		if (message != null) {
			final Unmarshaller unMarshaller = XMLutils.createUnmarshaller(jaxbContextName, cl);
			StringBuffer xmlStr = new StringBuffer(message);
			obj = unMarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())));
		}
		return obj;
	}

	/**
	 * @param message
	 * @param jaxbContextName
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return Object
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static Object unMarshal(final byte[] message, final String jaxbContextName, ClassLoader cl) throws JAXBException, UnsupportedEncodingException {
		Object obj = null;
		if (message != null) {
			final Unmarshaller unMarshaller = XMLutils.createUnmarshaller(jaxbContextName, cl);
			obj = unMarshaller.unmarshal(new StreamSource(new StringReader(new String(message, "UTF-8"))));
		}
		return obj;
	}

	/**
	 * @param xmlNode
	 * @param jaxbContextName
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return Object
	 * @throws JAXBException
	 */
	public static Object unMarshal(final Node xmlNode, final String jaxbContextName, ClassLoader cl) throws JAXBException {
		Object obj = null;
		if (xmlNode != null) {
			final Unmarshaller unMarshaller = XMLutils.createUnmarshaller(jaxbContextName, cl);
			obj = unMarshaller.unmarshal(xmlNode);
		}
		return obj;
	}

	/**
	 * Create a {@link Marshaller} for the given context and return it.
	 * 
	 * @param jaxbContextName the context name where to look to find jaxb classes
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return the created {@link Marshaller}
	 * @throws JAXBException
	 */
	public static Marshaller createMarshaller(final String jaxbContextName, ClassLoader cl) throws JAXBException {
		final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbContextName, cl);
		return jaxbContext.createMarshaller();
	}

	/**
	 * Marshal a jaxb object to obtain the xml format as a string.
	 * 
	 * @param jabxbObject the jaxb object
	 * @param jaxbContextName the contect name where to look to find jaxb classes
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return a string representing the jaxb object
	 * @throws JAXBException
	 */
	public static String marshal(Object jabxbObject, String jaxbContextName, ClassLoader cl) throws JAXBException {
		Marshaller marshaller = createMarshaller(jaxbContextName, cl);

		final ByteArrayOutputStream xmlStream = new ByteArrayOutputStream();
		marshaller.marshal(jabxbObject, xmlStream);

		return xmlStream.toString();
	}

	/**
	 * Marshal a jaxb object to obtain the xml format as a string in specific encoding.
	 * 
	 * @param jabxbObject the jaxb object
	 * @param jaxbContextName the contect name where to look to find jaxb classes
	 * @param cl : {@link ClassLoader} of the ObjectFactory
	 * @return a string representing the jaxb object
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public static String marshal(Object jabxbObject, String jaxbContextName, ClassLoader cl, String encoding) throws JAXBException,
			UnsupportedEncodingException
	{
		Marshaller marshaller = createMarshaller(jaxbContextName, cl);

		final ByteArrayOutputStream xmlStream = new ByteArrayOutputStream();
		marshaller.marshal(jabxbObject, xmlStream);

		return xmlStream.toString(encoding);
	}
	
	/**
	 * Parse une string contenant un document XML dans un DOM Element.
	 * 
	 * @param xml le document XML sous forme de string
	 * @return l'element racine du document (root element), ou null si erreur
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static Element stringToDomElement(final String xml) throws IOException, SAXException, ParserConfigurationException {
		Element element = null;
		if (xml != null) {
			final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			final Document doc = db.parse(new InputSource(new StringReader(xml)));
			element = doc.getDocumentElement();
		}
		return element;
	}

	public static XMLGregorianCalendar getXmlGregorianCalendarFromDate(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		XMLGregorianCalendar f = null;
		try {
			f = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		return f;
	}

	public String marshal(Object object) throws JAXBException, UnsupportedEncodingException {
		return marshal(object, object.getClass().getPackage().getName(), object.getClass().getClassLoader(), "UTF-8");
	}
	
	
}
