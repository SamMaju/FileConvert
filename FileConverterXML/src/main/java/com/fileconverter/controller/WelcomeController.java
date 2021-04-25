package com.fileconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fileconverter.service.FileConverterService;


/*
 * The class acts as a controller to recieve request from GUI
 */


@Controller
public class WelcomeController {
	
	@Autowired
	public FileConverterService fileconverterService;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		model.put("name", "Test");
		return "welcome";
	}
	
	@RequestMapping(value = "/convertToXML", method = RequestMethod.GET)
	public String convertTOXML() {
		return "xmlConvert";
	}
	
	@RequestMapping(value = "/convertToCSV", method = RequestMethod.GET)
	public String convertTOCSV() {
		return "csvConvert";
	}
	
	@PostMapping("/xmlConvert")
	public ModelAndView xmlConvert(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		if (file.isEmpty()) {
			return new ModelAndView("status", "message", "Please select a file and try again");
		}
		
		String status = fileconverterService.readFile(file, "XML");
		if(status.equals("Success")){
			return new ModelAndView("status", "message", "File Converted to XML Format sucessfully");
		}else{
			return new ModelAndView("status", "message", "File did not converted");
		}
		
	}
	
	@PostMapping("/csvConvert")
	public ModelAndView csvConvert(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		if (file.isEmpty()) {
			return new ModelAndView("status", "message", "Please select a file and try again");
		}
		String status = fileconverterService.readFile(file, "CSV");
		if(status.equals("Success")){
			return new ModelAndView("status", "message", "File Converted to XML Format sucessfully");
		}else{
			return new ModelAndView("status", "message", "File did not converted");
		}
	}

}
