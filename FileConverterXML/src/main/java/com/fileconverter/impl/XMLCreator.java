package com.fileconverter.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fileconverter.service.ConvertType;

@Component
public class XMLCreator implements ConvertType{
	
	private static final Logger logger = LoggerFactory.getLogger(XMLCreator.class);
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String TAB_SEPARATOR = "\t";
	private static final String TAG_TEXT = "text";
	private static final String TAG_SENTENCE = "sentence";
	private static final String TAG_WORD = "word";
	static int count = 0;

	/* (non-Javadoc)
	 * @see com.fileconverter.service.ConvertType#convert(java.io.OutputStream, java.util.Map, java.util.List, int)
	 * Method is used to create the XML file by iterating the list of word and sentencecounter2 hashmap having 
	 * number of sentence as key and each sentence as a value.
	 * Writing the response in the output file
	 * 
	 * 
	 */
	public String convert(OutputStream out, Map<Integer, List<String>> sentenceCounter,List<String> listTest2,
			int maxWordsInSentence ) {

		try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
			XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(writer);

			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent end = createNewLine(eventFactory);
			XMLEvent tab = createTab(eventFactory);

			// Create start tag
			StartDocument startDocument = eventFactory.createStartDocument();
			EndDocument endDocument = eventFactory.createEndDocument();
			eventWriter.add(startDocument);
			eventWriter.add(end);
			// create config open tag

			// Create text tag
			StartElement itemStartElement1 = eventFactory.createStartElement("", "", "text");
			eventWriter.add(itemStartElement1);
			for (Map.Entry<Integer, List<String>> entry : sentenceCounter.entrySet()) {
				eventWriter.add(end);
				eventWriter.add(tab);
				StartElement itemStartElement = eventFactory.createStartElement("", "", TAG_SENTENCE);
				eventWriter.add(itemStartElement);
				eventWriter.add(end);
				eventWriter.add(tab);
				// add words
				for (String words : entry.getValue()) {
					eventWriter.add(tab);
					createItemNode(eventFactory, eventWriter, TAG_WORD, words);
					eventWriter.add(tab);
				}

				EndElement itemEndElement = eventFactory.createEndElement("", "", TAG_SENTENCE);
				eventWriter.add(itemEndElement);

			}
			eventWriter.add(end);
			EndElement itemEndElement1 = eventFactory.createEndElement("", "", TAG_TEXT);
			eventWriter.add(itemEndElement1);

			eventWriter.add(endDocument);
			eventWriter.flush();
			eventWriter.close();

			return "success";

		} catch (XMLStreamException | FactoryConfigurationError | IOException e) {
			logger.debug("Exception while parsing XML file", e);
			return null;
		} catch (Exception e) {
			logger.debug("Exception while creating XML file", e);
			return null;
		}

	}

	/**
	 * @param eventFactory
	 * @param eventWriter
	 * @param elementName
	 * @param value
	 * @throws XMLStreamException
	 * Method is required to create node for XML
	 */
	private static void createItemNode(XMLEventFactory eventFactory, XMLEventWriter eventWriter, String elementName,
			String value) throws XMLStreamException {

		XMLEvent end = eventFactory.createDTD(NEW_LINE_SEPARATOR);
		StartElement startElement = eventFactory.createStartElement("", "", elementName);
		eventWriter.add(startElement);
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		EndElement endElement = eventFactory.createEndElement("", "", elementName);
		eventWriter.add(endElement);
		eventWriter.add(end);
	}

	private static XMLEvent createTab(XMLEventFactory eventFactory) {
		return eventFactory.createDTD(TAB_SEPARATOR);
	}

	private static XMLEvent createNewLine(XMLEventFactory eventFactory) {
		return eventFactory.createDTD(NEW_LINE_SEPARATOR);
	}


}
