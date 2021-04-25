package com.fileconverter.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SentenceConverterTest {

	@Test
	public void testConvertToCSV() {
		List<String> ls = new ArrayList<String>();
		ls.add("Mary");
		ls.add("had");
		ls.add("a");
		ls.add("liitle");
		ls.add("lamp");
		
		List<String> ls1 = new ArrayList<String>();
		ls1.add("Twinlkle");
		ls1.add("Twinlkle");
		ls1.add("little");
		ls1.add("Star");
		
		List<String> list = new ArrayList<String>();
		list.add("Mary");
		list.add("had");
		list.add("a");
		list.add("liitle");
		list.add("lamp");
		list.add("Twinlkle");
		list.add("Twinlkle");
		list.add("little");
		list.add("Star");
		
		
		Map <Integer ,List <String>> sentenceCounter= new HashMap <Integer ,List <String>>();
		sentenceCounter.put(1, ls);
		sentenceCounter.put(2, ls1);
		
		CSVCreator converter = new CSVCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		converter.convert(baos,sentenceCounter, list, 5);
		
		String expect =",Word 1,Word 2,Word 3,Word 4,Word 5\n" +
				"Sentence 1,Mary,had,a,liitle,lamp\n" +
				"Sentence 2,Twinlkle,Twinlkle,little,Star\n";
		//String s = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		//System.out.println(s);
		assertEquals(expect, new String(baos.toByteArray(), StandardCharsets.UTF_8));
	}

}
