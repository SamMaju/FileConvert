package com.fileconverter.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fileconverter.parser.ParseFile;
import com.fileconverter.service.FileConverterService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Saumabha Majumdar
 * FileConverterServiceImpl reads the file uploaded from the GUI 
 *
 */
@Component
@Slf4j
public class FileConverterServiceImpl implements FileConverterService {

	private static final Logger logger = LoggerFactory.getLogger(FileConverterService.class);
	static int count = 0;
	
	@Autowired
	public ParseFile parseFile;
	
	@Autowired
	public XMLCreator xmlCreator;
	
	@Autowired
	public CSVCreator csvCreator;
	

	/* (non-Javadoc)
	 * @see com.fileconverter.service.FileConverterService#readFile(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 * Method is used to read the file from GUI and read the content.
	 */
	public String readFile(MultipartFile filePath, String format) {
		String status = null;
		StringBuilder sb = new StringBuilder();
		
		try (BufferedReader br
			      = new BufferedReader(new InputStreamReader(filePath.getInputStream()))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			        	sb.append(line).append("\n");
			        }
			        status = convertFile(sb, format);
			    
		}catch(IOException e){
			logger.debug("Exception while reading file",e);
			status = "Failed";
		}
		
		return status;
	}
	
	
	
	
	
	/**
	 * @param sb
	 * @param format
	 * @return
	 * Method is used to differentiate the flow between CSV and XML file conversion logic.
	 * Return the success or failure status based on the outcome
	 * 
	 */
	public String convertFile(StringBuilder sb, String format){
		String status = "";
		try {
			List<String> sorted = new ArrayList<String>();
			Map<Integer, List<String>> hm = new HashMap<Integer, List<String>>();
			List<String> listTest =parseFile.parseToFormSentence(sb,sorted,hm);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (format.equalsIgnoreCase("XML")) {
				try {
					xmlCreator.convert(baos,hm, listTest, ParseFile.maxWordsInSentence);
					try(OutputStream outputStream = new FileOutputStream("ouput.xml")) {
						baos.writeTo(outputStream);
						status = "Success";
					}catch(IOException e){
						logger.debug("Exception while writing the output in file",e);
						status = "Failed";
					}
				} catch (FactoryConfigurationError e) {
					logger.debug("Exception while parsing XML file",e);
					status = "Failed";
				}
			} else {
				csvCreator.convert(baos,hm, listTest, ParseFile.maxWordsInSentence);
				try(OutputStream outputStream = new FileOutputStream("ouput1.csv")) {
					baos.writeTo(outputStream);
					status = "Success";
				}catch(IOException e){
					logger.debug("Exception while writing the output in file",e);
					status = "Failed";
				}
			}

		} catch (Exception e) {
			logger.debug("Exception in sentenceToXML method",e);
			status = "Failed";
		}

		return status;
	}
}