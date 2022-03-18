package com.martiniriarte.error.exceptions;

public class AlmacenamientoArchivoNoEncontradoException extends AlmacenamientoException {

	private static final long serialVersionUID = 8482217129851689197L;

	public AlmacenamientoArchivoNoEncontradoException(String message) {
		super(message);
	}

	public AlmacenamientoArchivoNoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

}
