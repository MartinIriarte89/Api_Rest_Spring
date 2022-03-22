package com.martiniriarte.error.exceptions;

public class UsuariosNoEncontradosException extends RuntimeException {

	private static final long serialVersionUID = 545251959753149288L;

	public UsuariosNoEncontradosException() {
		super("No se encontro la lista de usuarios");
	}
	
	public UsuariosNoEncontradosException(String string) {
		super(string);
	}
}
