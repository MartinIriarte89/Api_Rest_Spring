package com.martiniriarte.controlador;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.martiniriarte.dto.GetUsuarioJwtDto;
import com.martiniriarte.modelo.LoginRequest;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.seguridad.JwtProvider;
import com.martiniriarte.servicio.ServicioUsuario;
import com.martiniriarte.util.converter.UsuarioDtoConverter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AutenticacionControlador {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final UsuarioDtoConverter converter;
	private final ServicioUsuario servicioUsuario;

	@PostMapping("/auth/login")
	public ResponseEntity<GetUsuarioJwtDto> login(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UsuarioEntidad usuario = servicioUsuario.buscarPorNombreUsuario(authentication.getName()).orElse(null);
		String jwtToken = jwtProvider.generarToken(authentication);

		GetUsuarioJwtDto usuarioJwtDto = converter.convertUsuarioEntidadAGetUsuarioJwtDto(usuario);
		usuarioJwtDto.setToken(jwtToken);

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioJwtDto);

	}

}
