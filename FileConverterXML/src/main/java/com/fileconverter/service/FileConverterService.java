package com.fileconverter.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileConverterService {

	String readFile(MultipartFile filePath, String format);
}
