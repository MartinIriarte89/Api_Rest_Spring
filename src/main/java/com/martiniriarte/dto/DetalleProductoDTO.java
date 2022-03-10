package com.martiniriarte.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalleProductoDTO {
	
	private Long id;
	private String nombre;
	private float precio;
	private String categoriaNombre;
	
}
