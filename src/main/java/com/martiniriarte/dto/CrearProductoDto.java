package com.martiniriarte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearProductoDto {

	private String nombre;

	private float precio;

	private long categoriaId;
	
	private String imagen;

}
