package com.martiniriarte.error;

public class AlmacenamientoException extends RuntimeException {

	private static final long serialVersionUID = -5502351264978098291L;

	public AlmacenamientoException(String message) {
		super(message);
	}

	public AlmacenamientoException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
