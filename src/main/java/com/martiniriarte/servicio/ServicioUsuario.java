package com.martiniriarte.servicio;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.martiniriarte.dto.CrearUsuarioDto;
import com.martiniriarte.dto.GetUsuarioDto;
import com.martiniriarte.error.exceptions.NuevoUsuarioConDiferenteContrasenaException;
import com.martiniriarte.modelo.UsuarioEntidad;
import com.martiniriarte.repositorio.RepositorioUsuario;
import com.martiniriarte.servicio.base.ServicioBase;
import com.martiniriarte.util.converter.UsuarioDtoConverter;
import com.martiniriarte.util.enumerados.RolUsuario;

@Service
public class ServicioUsuario extends ServicioBase<UsuarioEntidad, Long, RepositorioUsuario> {

	private PasswordEncoder encoder;
	private UsuarioDtoConverter converter;

	@Autowired
	public ServicioUsuario(RepositorioUsuario repositorio, PasswordEncoder encoder, UsuarioDtoConverter converter) {
		super(repositorio);
		this.encoder = encoder;
		this.converter = converter;
	}

	public Optional<UsuarioEntidad> buscarPorNombreUsuario(String nombreUsuario) {
		return this.repositorio.findByNombreUsuario(nombreUsuario);
	}

	public GetUsuarioDto nuevoUsuario(CrearUsuarioDto crearUsuarioDto) {

		if (!crearUsuarioDto.getContrasena().contentEquals(crearUsuarioDto.getContrasena2())) {
			throw new NuevoUsuarioConDiferenteContrasenaException();
		}
		crearUsuarioDto.setContrasena(encoder.encode(crearUsuarioDto.getContrasena()));

		UsuarioEntidad usuario = converter.convertCrearUsuarioDtoAUsuarioEntidad(crearUsuarioDto);
		usuario.setRoles(Set.of(RolUsuario.USER));

		try {
			guardar(usuario);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya existe");
		}

		return converter.convertUsuarioEntidadAGetUsuarioDto(usuario);
	}
}
