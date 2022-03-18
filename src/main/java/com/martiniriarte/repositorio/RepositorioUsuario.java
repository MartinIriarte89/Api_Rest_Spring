package com.martiniriarte.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martiniriarte.modelo.UsuarioEntidad;

public interface RepositorioUsuario extends JpaRepository<UsuarioEntidad, Long> {

	Optional<UsuarioEntidad> findByNombreUsuario(String nombreUsuario);

}
