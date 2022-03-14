package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Categoria;
import com.martiniriarte.persistencia.CategoriaDAO;
import com.martiniriarte.servicio.base.ServicioBase;

@Service
public class ServicioCategoria extends ServicioBase<Categoria, Long, CategoriaDAO> {

	public ServicioCategoria(CategoriaDAO repositorio) {
		super(repositorio);
	}
}
