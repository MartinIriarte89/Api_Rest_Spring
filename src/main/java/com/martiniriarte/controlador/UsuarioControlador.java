package com.martiniriarte.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.martiniriarte.dto.CrearUsuarioDto;
import com.martiniriarte.dto.GetUsuarioDto;
import com.martiniriarte.error.exceptions.UsuariosNoEncontradosException;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.servicio.ServicioUsuario;
import com.martiniriarte.util.converter.UsuarioDtoConverter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioControlador {

	private final ServicioUsuario servicioUsuario;
	private final UsuarioDtoConverter converter;

	@GetMapping("/")
	public ResponseEntity<List<GetUsuarioDto>> obtenerUsuarios() {
		List<GetUsuarioDto> usuarios = servicioUsuario.buscarTodos().stream()
				.map(converter::convertUsuarioEntidadAGetUsuarioDto).collect(Collectors.toList());

		if (usuarios.isEmpty()) {
			throw new UsuariosNoEncontradosException();
		}

		return ResponseEntity.ok(usuarios);
	}

	@PostMapping("/")
	public ResponseEntity<GetUsuarioDto> nuevoUsuario(@RequestBody CrearUsuarioDto crearUsuarioDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(servicioUsuario.nuevoUsuario(crearUsuarioDto));
	}

	@GetMapping("/me")
	public ResponseEntity<GetUsuarioDto> yo(@AuthenticationPrincipal User user) {
		Optional<UsuarioEntidad> optionalUser = servicioUsuario.buscarPorNombreUsuario(user.getUsername());
		GetUsuarioDto usuarioDto = null;
		if (optionalUser.isPresent()) {
			UsuarioEntidad usuario = optionalUser.get();
			usuarioDto = converter.convertUsuarioEntidadAGetUsuarioDto(usuario);
		}
		
		return ResponseEntity.ok(usuarioDto);
	}

}
