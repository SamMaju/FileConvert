package com.fileconverter.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;



/**
 * @author Saumabha Majumdar
 * Class  is used to parse the file and tokenize to sentence and word
 *
 */

@Component
public class ParseFile {
	
	public static int maxWordsInSentence = 0;
	
	/**
	 * Method is used to parse the data from file to sentence and word and created a map to store the 
	 * number of sentence we have in file and the max length in a sentence.
	 * @param sb
	 * @param sorted
	 * @param hm
	 * @return
	 */
	public List<String>  parseToFormSentence(StringBuilder sb, List<String> sorted, Map<Integer, List<String>> hm) {
		
		int counter = 0;
		maxWordsInSentence = 0;
		String contents = sb.toString();
		Document doc = new Document(contents);
        	List<Sentence> sentences = doc.sentences();
		List <String> listTest=new ArrayList<String>(); 
		for (Sentence seperator : sentences) {
			List<String> words = seperator.words();
			List<String> wordSorted = new ArrayList<String>();
			for (String word: words) {
				if (!word.matches("\\p{Punct}")) {
					wordSorted.add(word);
				}
			}
			sorted = Arrays.asList(wordSorted.stream().sorted((p1, p2) -> p1.compareToIgnoreCase(p2)).toArray(String[]::new));
			if(sorted.size() > maxWordsInSentence) {
	            maxWordsInSentence = sorted.size();
	        }
			hm.put(counter++, sorted);
			listTest.addAll(sorted);
		}	
		return listTest;
	}

}
