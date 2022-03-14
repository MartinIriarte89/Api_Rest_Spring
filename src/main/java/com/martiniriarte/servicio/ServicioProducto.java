package com.martiniriarte.servicio;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Producto;
import com.martiniriarte.persistencia.ProductoDAO;
import com.martiniriarte.servicio.base.ServicioBase;

@Service

public class ServicioProducto extends ServicioBase<Producto, Long, ProductoDAO> {

	public ServicioProducto(ProductoDAO repositorio) {
		super(repositorio);
	}
}
