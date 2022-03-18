package com.martiniriarte.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.Categoria;

public interface RepositorioCategoria extends JpaRepository<Categoria, Long> {

}
