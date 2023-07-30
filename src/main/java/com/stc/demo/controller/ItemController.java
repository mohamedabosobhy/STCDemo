package com.stc.demo.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stc.demo.exception.AccessException;
import com.stc.demo.exception.FolderAleardyExistException;
import com.stc.demo.exception.PathNotFoundException;
import com.stc.demo.model.CreateItemRequest;
import com.stc.demo.services.ItemServices;

@RestController
@RequestMapping("/api")
public class ItemController {

	@Autowired
	ItemServices itemServices;

	@PostMapping("/createSpace")
	public ResponseEntity<?> createSpace(@RequestBody CreateItemRequest request) throws Exception {
		return new ResponseEntity<>(itemServices.createSpace(request), HttpStatus.CREATED);
	}

	@PostMapping("/createFolder")
	public ResponseEntity<?> createFolder(@RequestBody CreateItemRequest request) throws Exception {
		return new ResponseEntity<>(itemServices.createFolder(request), HttpStatus.CREATED);
	}

	@PostMapping("/createFile")
	public ResponseEntity<?> createFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String itemPath,
			@RequestParam("email") String email) throws Exception {
		return new ResponseEntity<>(itemServices.createFile(file, itemPath, email), HttpStatus.CREATED);

	}

	@GetMapping("/fileMetaData")
	public ResponseEntity<?> getFileMetaData(@RequestParam("path") String itemPath, @RequestParam("email") String email) throws AccessException, PathNotFoundException, FolderAleardyExistException
			 {
		return new ResponseEntity<>(itemServices.getFileMetaData(itemPath, email), HttpStatus.OK);
	}

	@GetMapping("/file")
	public ResponseEntity<?> getFile(@RequestParam("path") String itemPath, @RequestParam("email") String email)
			throws AccessException, PathNotFoundException, FolderAleardyExistException
			 {
		Pair<byte[], String> file = itemServices.getFile(itemPath, email);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getRight() + "\"")
				.body(file.getLeft());
	}

}