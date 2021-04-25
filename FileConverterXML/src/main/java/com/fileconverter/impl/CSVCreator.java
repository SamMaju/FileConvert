package com.fileconverter.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fileconverter.service.ConvertType;


/**
 * @author Saumabha Majumdar
 * CSV Creator Implementation logic is implemented 
 *
 */
@Component
public class CSVCreator implements ConvertType{
	
	private static final Logger logger = LoggerFactory.getLogger(CSVCreator.class);
	private static final String NEW_LINE_SEPARATOR = "\n";
	static int count = 0;
	private static final String ELEMENT_SENTENCE = "Sentence";
	private static final String ELEMENT_WORD = "Word";

	
	
	
	
	/* (non-Javadoc)
	 * @see com.fileconverter.service.ConvertType#convert(java.io.OutputStream, java.util.Map, java.util.List, int)
	 * Method is used to create the CSV file by iterating the list of word and sentencecounter2 hashmap having 
	 * number of sentence as key and each sentence as a value.
	 * Writing the response in the output file
	 * 
	 */
	public String convert(OutputStream out, Map<Integer, List<String>> sentenceCounter2,List<String> listTest2,
			int maxWordsInSentence) {
		CSVPrinter csvFilePrinter = null;
		try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
			csvFilePrinter = new CSVPrinter(writer, csvFileFormat);

			List<String> header = new ArrayList<>();
			header.add(null);
			IntStream.range(1, maxWordsInSentence + 1).forEach(value -> header.add(ELEMENT_WORD + " " + value));
			csvFilePrinter.printRecord(header);

			int i = 0;
			for (Map.Entry<Integer, List<String>> entry : sentenceCounter2.entrySet()) {
				List<String> words = new ArrayList<>();
				words.add(ELEMENT_SENTENCE + " " + (++i));

				for (String word : entry.getValue()) {
					words.add(word);
				}
				csvFilePrinter.printRecord(words);
			}
			writer.flush();
			csvFilePrinter.close();
		} catch (IOException e) {
			System.exit(-1);
		}
		
		return "sucess";
	}


}
