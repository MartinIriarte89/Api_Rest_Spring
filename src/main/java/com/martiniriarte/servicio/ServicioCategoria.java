package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Categoria;
import com.martiniriarte.persistencia.CategoriaDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioCategoria {

	private final CategoriaDAO categoriaDAO;
	
	public Categoria buscarPorId(long id) {
		return categoriaDAO.findById(id).orElse(null);
	}
	
}
