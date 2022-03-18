package com.martiniriarte.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaginaNoEncontradaExeption extends  RuntimeException{

	private static final long serialVersionUID = -7986292278195406770L;

	public PaginaNoEncontradaExeption(int numDePag) {
		super("No se puede encontrar la página: " + numDePag);
	}
}
