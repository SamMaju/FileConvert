package com.fileconverter.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ConvertType {
	
	public String convert(OutputStream out, Map<Integer, List<String>> sentenceCounter2,List<String> listTest2,
			int maxWordsInSentence);

}
