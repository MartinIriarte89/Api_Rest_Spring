package com.martiniriarte.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Pedido;

public interface PedidoDAO extends JpaRepository<Pedido, Long> {

}
