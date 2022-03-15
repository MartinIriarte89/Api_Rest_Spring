package com.martiniriarte.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

	@ApiModelProperty(value = "Nombre del producto", dataType = "String", example = "Juice - Orange, Concentrate", position = 1)
	private String nombre;

	@ApiModelProperty(value = "Precio del producto", dataType = "float", example = "91", position = 2)
	private float precio;

	@ApiModelProperty(value = "Id de la categoria del producto", dataType = "long", example = "2", position = 3)
	private long categoriaId;

	@ApiModelProperty(value = "Imagen del producto", dataType = "String", example = "http://www.midominio.com/files/12345-imagen.jpg", position = 4)
	private String imagen;
}
