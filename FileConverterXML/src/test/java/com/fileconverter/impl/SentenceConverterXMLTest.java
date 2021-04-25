package com.fileconverter.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SentenceConverterXMLTest {

	@Test
	public void testConvertToXML() {
		
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
		
		XMLCreator converter = new XMLCreator();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		converter.convert(baos, sentenceCounter, null, 0);

		String expect="<?xml version=\"1.0\" encoding=\"UTF-8\""+"?>\n" +
        		"<text>\n\t" +
        		"<sentence>\n\t\t" +"<word>Mary</word>\n\t\t" +"<word>had</word>\n\t\t" +"<word>a</word>\n\t\t" +"<word>liitle</word>\n\t\t" +"<word>lamp</word>\n\t" +"</sentence>\n\t" +
        		"<sentence>\n\t\t" +"<word>Twinlkle</word>\n\t\t" +"<word>Twinlkle</word>\n\t\t" +"<word>little</word>\n\t\t" +"<word>Star</word>\n\t" +"</sentence>\n" +
        		"</text>";

		
		
		assertEquals(expect, new String(baos.toByteArray(), StandardCharsets.UTF_8));
		
		
		
	}

}
