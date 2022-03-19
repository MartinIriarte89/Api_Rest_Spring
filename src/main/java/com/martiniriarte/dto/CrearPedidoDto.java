package com.martiniriarte.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearPedidoDto {

	private List<CrearLineaPedidoDto> lineas;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class CrearLineaPedidoDto {

		private int cantidad;
		private Long productoId;

	}
}
