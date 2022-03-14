package com.martiniriarte.error;

public class BuscarProductoSinResultadoException extends RuntimeException {

	private static final long serialVersionUID = -5200037489960858414L;

	public BuscarProductoSinResultadoException() {
		super("La búsqueda de productos no produjo resultados");
	}

	public BuscarProductoSinResultadoException(String txt) {
		super(String.format("El término de búsqueda %s no produjo resultados", txt));
	}

}
