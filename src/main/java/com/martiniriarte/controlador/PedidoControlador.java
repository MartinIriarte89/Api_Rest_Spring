package com.martiniriarte.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.martiniriarte.dto.CrearPedidoDto;
import com.martiniriarte.dto.GetPedidoDto;
import com.martiniriarte.error.exceptions.PedidoNoEncontradoExcepcion;
import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.servicio.ServicioPedido;
import com.martiniriarte.util.paginacion.PaginacionLinks;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoControlador {

	private final ServicioPedido servicioPedido;
	private final PaginacionLinks paginacionLinks;

	@GetMapping("/")
	public ResponseEntity<List<Pedido>> pedidos(Pageable pageable, HttpServletRequest request) {

		Page<Pedido> pedidos = servicioPedido.buscarTodos(pageable);

		if (pedidos.isEmpty()) {
			throw new PedidoNoEncontradoExcepcion();
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

		return ResponseEntity.ok().header("link", paginacionLinks.crearLinkHeader(pedidos, builder))
				.body(pedidos.getContent());
	}
	
	@PostMapping("/")
	public GetPedidoDto nuevoPedido(CrearPedidoDto pedido) {
		return null;
	}

}
