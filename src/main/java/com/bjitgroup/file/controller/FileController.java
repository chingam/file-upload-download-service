package com.bjitgroup.file.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bjitgroup.file.service.FileStorageService;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/upload")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
		Map<String, Object> resposeData = new HashMap<>();
		resposeData.put("status", "success");
		resposeData.put("file", fileStorageService.storeFile(file));
		return ResponseEntity.ok(resposeData);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
		Map<String, Object> resposeData = new HashMap<>();
		resposeData.put("status", "success");
		resposeData.put("file", fileStorageService.getFileWithBase64(fileName));
		return ResponseEntity.ok(resposeData);
	}
}
