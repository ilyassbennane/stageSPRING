package com.ramadan.api.exceptions.exceptionhandling;

import java.io.Serializable;

/**
 * Exception handler.
 * 
 * @author HRAIDA El Mehdi
 *
 */
public interface ExceptionHandler extends Serializable {

	/**
	 * 
	 * Methode handle : Handle the exception.
	 *
	 * @param th
	 *            a throwable to handle.
	 */
	void handle(Throwable th);

}
