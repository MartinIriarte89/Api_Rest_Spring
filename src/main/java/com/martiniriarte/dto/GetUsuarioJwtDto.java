package com.martiniriarte.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetUsuarioJwtDto extends GetUsuarioDto{

	private String token;
}
