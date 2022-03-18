package com.martiniriarte.error.exceptions;

public class NuevoUsuarioConDiferenteContrasenaException extends RuntimeException {
	
	private static final long serialVersionUID = 6137429658594884215L;

	public NuevoUsuarioConDiferenteContrasenaException() {
		super("La contraseña no coincide");
	}
	
}
