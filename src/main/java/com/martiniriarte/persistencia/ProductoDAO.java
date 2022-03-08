package com.martiniriarte.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Producto;

public interface ProductoDAO extends JpaRepository<Producto, Long>{

}
