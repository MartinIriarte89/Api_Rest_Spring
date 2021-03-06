package com.martiniriarte.modelo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="cliente_id")
	private UsuarioEntidad cliente;

	@CreatedDate
	private LocalDateTime fecha;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@Builder.Default
	@JsonManagedReference
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineaPedido> lineas = new HashSet<>();
	
	public float getTotal() {
		return (float) lineas.stream().mapToDouble(LineaPedido::getSubTotal).sum();
	}
	
	/**
	 * Métodos helper
	 */
	
	public void agregarLineaPedido(LineaPedido lineaPedido) {
		lineas.add(lineaPedido);
		lineaPedido.setPedido(this);
	}
	
	public void elimimarLineaPedido(LineaPedido lineaPedido) {
		lineas.remove(lineaPedido);
		lineaPedido.setPedido(null);
	}
}
