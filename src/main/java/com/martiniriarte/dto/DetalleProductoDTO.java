package com.martiniriarte.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalleProductoDTO {
	
	@ApiModelProperty(value = "Id del producto", dataType = "long", example = "1", position = 1)
	private Long id;
	
	@ApiModelProperty(value = "Nombre del producto", dataType = "String", example = "Juice - Orange, Concentrate", position = 2)
	private String nombre;
	
	@ApiModelProperty(value = "Precio del producto", dataType = "float", example = "91", position = 3)
	private float precio;
	
	@ApiModelProperty(value = "Categoria del producto", dataType = "String", example = "Bebida", position = 4)
	private String categoriaNombre;
	
	@ApiModelProperty(value = "Imagen del producto", dataType = "String", example = "http://www.midominio.com/files/12345-imagen.jpg", position = 5)
	private String imagen;
	
}