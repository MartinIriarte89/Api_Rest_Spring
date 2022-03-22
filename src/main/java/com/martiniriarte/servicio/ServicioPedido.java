package com.martiniriarte.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.martiniriarte.dto.CrearPedidoDto;
import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.repositorio.RepositorioPedido;
import com.martiniriarte.servicio.base.ServicioBase;
import com.martiniriarte.util.converter.PedidoDtoConverter;

@Service
public class ServicioPedido extends ServicioBase<Pedido, Long, RepositorioPedido> {

	private final PedidoDtoConverter converter;

	public ServicioPedido(RepositorioPedido repositorio, PedidoDtoConverter converter) {
		super(repositorio);
		this.converter = converter;
	}

	public Pedido nuevoPedido(CrearPedidoDto nuevo, UsuarioEntidad cliente) {
		Pedido pedido = converter.convertirCrearPedidoDtoAPedido(nuevo);
		pedido.setCliente(cliente);

		return pedido;
	}
	
	public Page<Pedido> buscarTodosPorUsuario(UsuarioEntidad usuario, Pageable pageable){
		return this.repositorio.findByCliente(usuario, pageable);
	}

}
