package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Categoria;
import com.martiniriarte.repositorio.RepositorioCategoria;
import com.martiniriarte.servicio.base.ServicioBase;

@Service
public class ServicioCategoria extends ServicioBase<Categoria, Long, RepositorioCategoria> {

	public ServicioCategoria(RepositorioCategoria repositorio) {
		super(repositorio);
	}
}
