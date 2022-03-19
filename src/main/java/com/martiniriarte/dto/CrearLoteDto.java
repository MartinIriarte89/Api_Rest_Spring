package com.martiniriarte.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearLoteDto {

	private String nombre;
	
	private List<Long> productos = new ArrayList<>();
}
