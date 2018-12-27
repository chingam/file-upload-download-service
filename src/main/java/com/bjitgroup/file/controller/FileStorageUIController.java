package com.bjitgroup.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjitgroup.file.FileStorageProperties;
import com.bjitgroup.file.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/filestorage")
public class FileStorageUIController {

	@Autowired
	private FileStorageProperties fileStorageProperties;

	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping
	public String upload(final ModelMap model) {
		model.addAttribute("brand", "File Upload");
		model.addAttribute("page", "upload");
		return "template";
	}

	@GetMapping(path = "/all")
	public String getFiles(final ModelMap model) {
		model.put("list", fileStorageService.getFiles());
		return "upload/table";
	}

	@GetMapping(path = "/download/{name}")
	public ResponseEntity<byte[]> downloadDocuemnt(@PathVariable("name") String name, HttpServletRequest req,
			HttpServletResponse res, final ModelMap model, Locale locale) {

		String url = fileStorageProperties.getUploadDir() + File.separator + name;
		File file = new File(url);
		if (!file.exists()) {
			log.warn("File: " + url + " not found");
			String errorMesg = "File not found";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("text", "plain"));
			return new ResponseEntity<>(errorMesg.getBytes(), headers, HttpStatus.NOT_FOUND);
		}

		final HttpHeaders headers = new HttpHeaders();
		byte[] bytes = null;
		try (InputStream input = new FileInputStream(file)) {
			bytes = IOUtils.toByteArray(input);
		} catch (Exception e) {
			log.error("File not found");
		}
		log.info("File should be downloaded: " + name);
		String headerValue = String.format("attachment; filename=\"%s\"", name);
		headers.add("Content-Disposition", headerValue);
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}

}
