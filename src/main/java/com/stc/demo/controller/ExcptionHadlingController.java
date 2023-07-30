package com.stc.demo.controller;

import com.stc.demo.exception.AccessException;
import com.stc.demo.exception.FolderAleardyExistException;
import com.stc.demo.exception.PathNotFoundException;
import com.stc.demo.exception.SpaceAleardyExistException;
import com.stc.demo.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExcptionHadlingController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SpaceAleardyExistException.class)
	public ResponseEntity<Object> handleSpaceAleardyExistException(SpaceAleardyExistException ex, WebRequest request) {
		return new ResponseEntity<>(Error.builder().message(ex.getMessage()).status("400").errorCode("1001").build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PathNotFoundException.class)
	public ResponseEntity<Object> handlePathNotFoundException(PathNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(Error.builder().message(ex.getMessage()).status("404").errorCode("1002").build(),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FolderAleardyExistException.class)
	public ResponseEntity<Object> handleFolderAleardyExistException(FolderAleardyExistException ex,
			WebRequest request) {
		return new ResponseEntity<>(Error.builder().message(ex.getMessage()).status("400").errorCode("1003").build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessException.class)
	public ResponseEntity<Object> handleAccessException(AccessException ex, WebRequest request) {
		return new ResponseEntity<>(Error.builder().message(ex.getMessage()).status("401").errorCode("1004").build(),
				HttpStatus.BAD_REQUEST);
	}
}
