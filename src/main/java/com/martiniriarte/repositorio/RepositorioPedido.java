package com.martiniriarte.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Pedido;
import com.martiniriarte.modelo.UsuarioEntidad;

public interface RepositorioPedido extends JpaRepository<Pedido, Long> {

	Page<Pedido> findByCliente(UsuarioEntidad usuario, Pageable pageable);
	
}
