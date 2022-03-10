package com.martiniriarte.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Categoria;

public interface CategoriaDAO extends JpaRepository<Categoria, Long> {

}
