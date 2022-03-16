package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.persistencia.PedidoDAO;
import com.martiniriarte.servicio.base.ServicioBase;

@Service
public class ServicioPedido extends ServicioBase<Pedido, Long, PedidoDAO> {

	public ServicioPedido(PedidoDAO repositorio) {
		super(repositorio);
	}

}
