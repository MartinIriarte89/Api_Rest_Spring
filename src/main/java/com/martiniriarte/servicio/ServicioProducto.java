package com.martiniriarte.servicio;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.martiniriarte.controlador.FicheroControlador;
import com.martiniriarte.dto.CrearProductoDto;
import com.martiniriarte.dto.GetProductoDto;
import com.martiniriarte.modelo.Producto;
import com.martiniriarte.repositorio.RepositorioProducto;
import com.martiniriarte.servicio.base.ServicioBase;
import com.martiniriarte.util.converter.ProductoDtoConverter;

@Service
public class ServicioProducto extends ServicioBase<Producto, Long, RepositorioProducto> {

	private final ServicioAlmacenamiento servicioAlmacenamiento;
	private final ProductoDtoConverter converter;

	@Autowired
	public ServicioProducto(RepositorioProducto repositorio, ServicioAlmacenamiento almacenamiento,
			ProductoDtoConverter converter) {
		super(repositorio);
		this.converter = converter;
		this.servicioAlmacenamiento = almacenamiento;
	}

	public GetProductoDto nuevoProducto(CrearProductoDto dto, MultipartFile file) {

		String urlImagen = null;

		if (!file.isEmpty()) {
			String imagen = servicioAlmacenamiento.store(file);
			urlImagen = MvcUriComponentsBuilder.fromMethodName(FicheroControlador.class, "serveFile", imagen, null)
					.build().toUriString();
		}

		dto.setImagen(urlImagen);
		Producto producto = converter.convertirCrearProductoDtoAProducto(dto);
		
		return converter.convertirProductoAProductoDto(guardar(producto));
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

	public Optional<Producto> findByIdConLotes(Long id) {
		return repositorio.findByIdJoinFetch(id);
	}
}
