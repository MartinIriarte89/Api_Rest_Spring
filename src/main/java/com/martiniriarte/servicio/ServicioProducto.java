package com.martiniriarte.servicio;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Producto;
import com.martiniriarte.repositorio.RepositorioProducto;
import com.martiniriarte.servicio.base.ServicioBase;

@Service

public class ServicioProducto extends ServicioBase<Producto, Long, RepositorioProducto> {

	public ServicioProducto(RepositorioProducto repositorio) {
		super(repositorio);
	}

	public Page<Producto> buscarPorNombre(String txt, Pageable pageable) {
		return this.repositorio.findByNombreContainsIgnoreCase(txt, pageable);
	}

	public Page<Producto> findByArgs(final Optional<String> nombre, final Optional<Float> precio, Pageable pageable) {

		// Uso clase anonima para evitar tener que crear una clase que implemente la
		// interfaz
		Specification<Producto> specNombreProducto = new Specification<Producto>() {

			private static final long serialVersionUID = 751059801070621371L;

			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (nombre.isPresent()) {
					return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.get() + "%");
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // No se filtra nada
				}
			}

		};

		Specification<Producto> specPrecioMenorQue = new Specification<Producto>() {

			private static final long serialVersionUID = 2450727362560973350L;

			@Override
			public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if (precio.isPresent()) {
					return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precio.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}

		};

		Specification<Producto> ambas = specNombreProducto.and(specPrecioMenorQue);

		return this.repositorio.findAll(ambas, pageable);

	}
}
