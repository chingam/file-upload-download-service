package com.bjitgroup.file.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bjitgroup.file.FileStorageProperties;
import com.bjitgroup.file.exception.FileNotFoundException;
import com.bjitgroup.file.exception.FileStorageException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageService {

	@Autowired
	private FileStorageProperties fileStorageProperties;
	
	List<String> files = new ArrayList<>();

	public String storeFile(MultipartFile file) {
		if (file == null) {
			log.info("File not found");
			throw new FileStorageException("File not found");
		}

		String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String fileName = String.format("%s.%s", UUID.randomUUID().toString().replace("-", ""), fileExtension);
		Path targetLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().resolve(fileName);

		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			files.add(fileName);
			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	
	public String getFileWithBase64(String fileName) {
		File file = new File(fileStorageProperties.getUploadDir() + File.separator + fileName);
		if (!file.exists()) {
			log.info("Could not found file {}" , fileName);
			throw new FileNotFoundException("Could not found file " + fileName);
		}
		try {
			return com.bjitgroup.file.utils.Base64Converter.encodeFile(file.getAbsolutePath());
		} catch (Exception e) {
			log.error("Could not convert file to Base64");
			throw new FileStorageException("Could not convert file to Base64" + fileName + ". Please try again!", e);
		}
	}
	
	public List<String> getFiles() {
		return files;
	}

}
