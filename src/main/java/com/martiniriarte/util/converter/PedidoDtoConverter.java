package com.martiniriarte.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.martiniriarte.dto.CrearPedidoDto;
import com.martiniriarte.dto.GetPedidoDto;
import com.martiniriarte.modelo.Pedido;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoDtoConverter {

	private final ModelMapper mapper;

	public Pedido convertirCrearPedidoDtoAPedido(CrearPedidoDto crearPedidoDto) {
		return mapper.map(crearPedidoDto, Pedido.class);
	}

	public Pedido convertirGetPedidoDtoAPedido(GetPedidoDto getPedidoDto) {
		return mapper.map(getPedidoDto, Pedido.class);
	}

	public CrearPedidoDto convertirPedidoACrearPedidoDto(Pedido pedido) {
		return mapper.map(pedido, CrearPedidoDto.class);
	}

	public GetPedidoDto convertirPedidoAGetPedidoDto(Pedido pedido) {
		return mapper.map(pedido, GetPedidoDto.class);
	}

}
