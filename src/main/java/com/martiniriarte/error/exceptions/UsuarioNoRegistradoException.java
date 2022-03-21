package com.martiniriarte.error.exceptions;

public class UsuarioNoRegistradoException extends RuntimeException {

	private static final long serialVersionUID = -68542066211839508L;
	
	public UsuarioNoRegistradoException() {
		super("No existe un usuario registrado");
	}

}
