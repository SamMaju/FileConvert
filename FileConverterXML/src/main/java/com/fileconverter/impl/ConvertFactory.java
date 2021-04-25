package com.fileconverter.impl;

import org.springframework.stereotype.Component;

import com.fileconverter.service.ConvertType;

@Component
public class ConvertFactory {
	
	public ConvertType convert(String format){
		
		if(format == null){
	         return null;
	      }		
	      if(format.equalsIgnoreCase("XML")){
	         return new XMLCreator();
	         
	      } else if(format.equalsIgnoreCase("CSV")){
	         return new CSVCreator();
	         
	      } 
	      return null;
	}

}
