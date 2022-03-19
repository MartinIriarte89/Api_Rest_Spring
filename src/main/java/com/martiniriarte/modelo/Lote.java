package com.martiniriarte.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Lote.class)
public class Lote {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name = "lote_id"), 
			inverseJoinColumns = @JoinColumn(name = "producto_id")
	)
	@Builder.Default
	private Set<Producto> productos = new HashSet<>();


	public void agregarProducto(Producto producto) {
		this.productos.add(producto);
		producto.getLotes().add(this);
	}

	public void borrarProducto(Producto p) {
		this.productos.remove(p);
		p.getLotes().remove(this);
	}
}
