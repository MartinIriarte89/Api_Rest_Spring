package com.martiniriarte.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Pedido;

public interface RepositorioPedido extends JpaRepository<Pedido, Long> {

}
