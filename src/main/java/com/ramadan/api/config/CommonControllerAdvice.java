package com.ramadan.api.config;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.exceptions.NotFoundIdException;
import com.ramadan.api.exceptions.WrongCodeVerificationException;

@ControllerAdvice
public class CommonControllerAdvice {

	@Autowired
	private com.ramadan.api.exceptions.exceptionhandling.ExceptionHandler exceptionHandler;
	


	@ExceptionHandler({ NotFoundIdException.class })
	public ResponseEntity<Object> handle(NotFoundIdException e) {
		exceptionHandler.handle(e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getApiError());

	}
	
	@ExceptionHandler({ WrongCodeVerificationException.class })
	public ResponseEntity<Object> handle(WrongCodeVerificationException e) {
		exceptionHandler.handle(e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getApiError());

	}
	
	@ExceptionHandler({ ApiKeyException.class })
	public ResponseEntity<Object> handle(ApiKeyException e) {
		exceptionHandler.handle(e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getApiError());

	}
	
	
	@ExceptionHandler({ APIErrorException.class })
	public ResponseEntity<Object> handle(APIErrorException e) {
		exceptionHandler.handle(e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getApiError());

	}
	

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public ResponseEntity<Object> handle(MaxUploadSizeExceededException e) {
		exceptionHandler.handle(e);
		ErrorCode code = ErrorCode.A205;
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(code, code.toString()));

	}
	

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handle(MethodArgumentNotValidException e) {
		
		exceptionHandler.handle(e);
		
		StringBuilder str = new StringBuilder(); 
		
		str.append("Arguments non valides :") ;
		
        for (final FieldError error : e.getBindingResult().getFieldErrors()) {
        	str.append(" ["+error.getField() + ": " + error.getDefaultMessage()+"]");
        }
        for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
        	str.append(" ["+error.getObjectName() + ": " + error.getDefaultMessage()+"]");
        }
       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ErrorCode.A209, str.toString()));
        
    }
	

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handle(Exception e) {
		exceptionHandler.handle(e);
		ErrorCode code = ErrorCode.A209;
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(code, code.toString()));

	}
	
	


}
