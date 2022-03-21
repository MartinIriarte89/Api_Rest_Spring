package com.martiniriarte.controlador;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.martiniriarte.dto.CrearPedidoDto;
import com.martiniriarte.dto.GetPedidoDto;
import com.martiniriarte.error.exceptions.PedidoNoEncontradoExcepcion;
import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.servicio.ServicioPedido;
import com.martiniriarte.servicio.ServicioUsuario;
import com.martiniriarte.util.converter.PedidoDtoConverter;
import com.martiniriarte.util.enumerados.RolUsuario;
import com.martiniriarte.util.paginacion.PaginacionLinks;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoControlador {

	private final ServicioPedido servicioPedido;
	private final PaginacionLinks paginacionLinks;
	private final ServicioUsuario servicioUsuario;
	private final PedidoDtoConverter converter;

	@GetMapping("/")
	public ResponseEntity<List<GetPedidoDto>> pedidos(Pageable pageable, HttpServletRequest request,
			@AuthenticationPrincipal User user) {
		Optional<UsuarioEntidad> optionalUser = servicioUsuario.buscarPorNombreUsuario(user.getUsername());
		UsuarioEntidad usuario = null;
		
		if (optionalUser.isPresent()) {
			usuario = optionalUser.get();
		}
		Page<Pedido> pedidos =  servicioPedido.buscarTodos(pageable);
		
		if(usuario != null && !usuario.getRoles().contains(RolUsuario.ADMIN)) {
			pedidos = servicioPedido.buscarTodosPorUsuario(usuario, pageable);
		}
	
		if (pedidos.isEmpty()) {
			throw new PedidoNoEncontradoExcepcion();
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
		Page<GetPedidoDto> pedidosDto = pedidos.map(converter::convertirPedidoAGetPedidoDto);
		
		return ResponseEntity.ok().header("link", paginacionLinks.crearLinkHeader(pedidos, builder))
				.body(pedidosDto.getContent());
	}

	@PostMapping("/")
	public ResponseEntity<GetPedidoDto> nuevoPedido(@RequestBody CrearPedidoDto crearPedidoDto, @AuthenticationPrincipal User user) {
		Optional<UsuarioEntidad> optionalUser = servicioUsuario.buscarPorNombreUsuario(user.getUsername());
		UsuarioEntidad usuario = null;

		if (optionalUser.isPresent()) {
			usuario = optionalUser.get();
		}

		Pedido pedido = servicioPedido.nuevoPedido(crearPedidoDto, usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(converter.convertirPedidoAGetPedidoDto(pedido));
	}

}
