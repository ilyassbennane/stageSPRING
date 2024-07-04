package com.ramadan.api.exceptions;

public class TechnicalException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5279365528846355012L;

	public TechnicalException(String message) {
		super(message);
	}
	
	public TechnicalException(Throwable cause) {
        super(cause);
    }


}
