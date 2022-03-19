package com.martiniriarte.error.exceptions;

public class CrearLoteException extends RuntimeException {

	private static final long serialVersionUID = -6656042484402959674L;

	public CrearLoteException() {
		super("Error al crear un nuevo lote");
	}
}
