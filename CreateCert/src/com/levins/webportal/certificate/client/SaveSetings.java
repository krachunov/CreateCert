package com.levins.webportal.certificate.client;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SaveSetings {
	private static final String FILE_SAVE_SETTINGS = "resources/clientSetings.xml";

	// TODO
	@SuppressWarnings("unused")
	private  void saveToFileXML(Document document)
			throws TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(
				FILE_SAVE_SETTINGS));
		transformer.transform(domSource, streamResult);
		System.out.println("File saved");
	}

	@SuppressWarnings("unused")
	private static Document createDomDocument(File fileToParse)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(fileToParse);
		return document;
	}
}
