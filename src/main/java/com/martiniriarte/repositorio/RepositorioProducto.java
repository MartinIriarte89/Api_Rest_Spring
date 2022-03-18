package com.martiniriarte.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.martiniriarte.modelo.Producto;

public interface RepositorioProducto extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto>{

	Page<Producto> findByNombreContainsIgnoreCase(String txt, Pageable pageable);
	
}
