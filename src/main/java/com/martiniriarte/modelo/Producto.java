package com.martiniriarte.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Producto {

	@ApiModelProperty(value = "Id del producto", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Nombre del producto", dataType = "String", example = "Juice - Orange, Concentrate", position = 2)
	private String nombre;

	@ApiModelProperty(value = "Precio del producto", dataType = "float", example = "91", position = 3)
	private float precio;

	@ApiModelProperty(value = "Imagen del producto", dataType = "String", example = "http://www.midominio.com/files/12345-imagen.jpg", position = 4)
	private String imagen;

	@ApiModelProperty(value = "Categoria del producto", dataType = "Categoria", position = 5)
	@ManyToOne()
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "productos")
	private Set<Lote> lotes = new HashSet<>();
}
