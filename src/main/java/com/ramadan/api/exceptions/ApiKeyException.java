package com.ramadan.api.exceptions;


public class ApiKeyException extends APIErrorException {


	
	private static final long serialVersionUID = 1L;

	public ApiKeyException(ErrorCode code) {
        super(code);
    }


}
