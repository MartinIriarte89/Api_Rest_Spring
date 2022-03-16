package com.martiniriarte.error;

public class PedidoNoEncontradoExcepcion extends RuntimeException{

	private static final long serialVersionUID = -5770248353265533529L;
	
	public PedidoNoEncontradoExcepcion() {
		super("No se han encontrado pedidos");
	}
	
	public PedidoNoEncontradoExcepcion(Long id) {
		super("No se ha encontrado pedido con el Id: " + id);
	}

}
