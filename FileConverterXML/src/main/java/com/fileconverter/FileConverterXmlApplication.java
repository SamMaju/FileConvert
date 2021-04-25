package com.fileconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fileconverter.service.FileConverterService;


/**
 * @author Saumabha Majumdar
 * Main class used for converting XML-CSV based on the user input
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="com")
public class FileConverterXmlApplication {
	private static final Logger logger = LoggerFactory.getLogger(FileConverterService.class);
	public static void main(String[] args) {
		
		try {
			SpringApplication.run(FileConverterXmlApplication.class, args);

		} catch (Exception e) {
			logger.debug("Exception while running FileConverterXmlApplication",e);
			System.exit(0);
		}
	}
	
	

}
