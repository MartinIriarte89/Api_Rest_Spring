package com.martiniriarte.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.martiniriarte.modelo.Producto;
import com.martiniriarte.persistencia.ProductoDAO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioProducto {

	private final ProductoDAO productoDAO;

	public List<Producto> buscarTodos() {
		return productoDAO.findAll();
	}

	public Producto buscarPorId(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

	public Producto guardar(Producto producto) {
		return productoDAO.save(producto);
	}

	public boolean existePorId(Long id) {
		return productoDAO.existsById(id);
	}

	public void borrar(Producto producto) {
		productoDAO.delete(producto);
	}
}
