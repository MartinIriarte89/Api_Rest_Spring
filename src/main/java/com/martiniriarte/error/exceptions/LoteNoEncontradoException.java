package com.martiniriarte.error.exceptions;

public class LoteNoEncontradoException extends Exception {

	private static final long serialVersionUID = 3342034462033626819L;

	public LoteNoEncontradoException() {
		super("No se han encontrado lotes de productos");
	}

	public LoteNoEncontradoException(Long id) {
		super("No se ha encontrado ningún lote con el ID " + id);
	}
}
