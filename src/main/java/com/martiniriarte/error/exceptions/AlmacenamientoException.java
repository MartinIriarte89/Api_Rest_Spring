package com.martiniriarte.error.exceptions;

public class AlmacenamientoException extends RuntimeException {
	
	private static final long serialVersionUID = -3316361048062749868L;

	public AlmacenamientoException(String message) {
		super(message);
	}

	public AlmacenamientoException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
