package com.martiniriarte.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetUsuarioDto {
	
	private String nombreUsuario;
	private String urlAvatar;
	private Set<String> roles;
}
