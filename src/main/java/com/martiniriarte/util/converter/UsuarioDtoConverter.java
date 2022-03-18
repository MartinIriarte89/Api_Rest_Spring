package com.martiniriarte.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.martiniriarte.dto.CrearUsuarioDto;
import com.martiniriarte.dto.GetUsuarioDto;
import com.martiniriarte.modelo.UsuarioEntidad;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioDtoConverter {

	private final ModelMapper mapper;

	public GetUsuarioDto convertUsuarioEntidadAGetUsuarioDto(UsuarioEntidad usuario) {
		return mapper.map(usuario, GetUsuarioDto.class);
	}
	
	public CrearUsuarioDto convertUsuarioEntidadACrearUsuarioDto(UsuarioEntidad usuario) {
		return mapper.map(usuario, CrearUsuarioDto.class);
	}
	
	public UsuarioEntidad convertGetUsuarioDtoAUsuarioEntidad(GetUsuarioDto getUsuarioDto) {
		return mapper.map(getUsuarioDto, UsuarioEntidad.class);
	}
	
	public UsuarioEntidad convertCrearUsuarioDtoAUsuarioEntidad(CrearUsuarioDto crearUsuarioDto) {
		return mapper.map(crearUsuarioDto, UsuarioEntidad.class);
	}
}
