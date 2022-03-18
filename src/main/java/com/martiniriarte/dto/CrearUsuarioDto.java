package com.martiniriarte.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearUsuarioDto {

	private String nombreUsuario;
	private String urlAvatar;
	private String contrasena;
	private String contrasena2;
}
