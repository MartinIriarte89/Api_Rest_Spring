package com.martiniriarte.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.martiniriarte.dto.views.ProductoViews;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

	@JsonView(ProductoViews.Dto.class)
	@ApiModelProperty(value = "Id del producto", dataType = "long", example = "1", position = 1)
	private Long id;
	
	@JsonView(ProductoViews.DtoCrear.class)
	@ApiModelProperty(value = "Nombre del producto", dataType = "String", example = "Juice - Orange, Concentrate", position = 2)
	private String nombre;
	
	@JsonView(ProductoViews.DtoCrear.class)
	@ApiModelProperty(value = "Precio del producto", dataType = "float", example = "91", position = 3)
	private float precio;
	
	@JsonView(ProductoViews.DtoCrear.class)
	@ApiModelProperty(value = "Id de la categoria del producto", dataType = "long", example = "2", position = 3)
	private long categoriaId;
	
	@JsonView(ProductoViews.Dto.class)
	@ApiModelProperty(value = "Categoria del producto", dataType = "String", example = "Bebida", position = 4)
	private String categoriaNombre;
	
	@JsonView(ProductoViews.DtoCrear.class)
	@ApiModelProperty(value = "Imagen del producto", dataType = "String", example = "http://www.midominio.com/files/12345-imagen.jpg", position = 5)
	private String imagen;
}
