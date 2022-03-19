package com.martiniriarte.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPedidoDto {

	private String clienteNombreUsuario;
	private String clienteUrlAvatar;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime fecha;
	private Set<GetLineaPedidoDto> lineas;
	private float total;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GetLineaPedidoDto {

		private String productoNombre;
		private int cantidad;
		private float precio;
		private float subTotal;

	}

}
