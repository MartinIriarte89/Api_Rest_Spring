package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.repositorio.RepositorioPedido;
import com.martiniriarte.servicio.base.ServicioBase;

@Service
public class ServicioPedido extends ServicioBase<Pedido, Long, RepositorioPedido> {

	public ServicioPedido(RepositorioPedido repositorio) {
		super(repositorio);
	}

}
