package com.martiniriarte.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.martiniriarte.modelo.Producto;
import com.martiniriarte.servicio.ServicioProducto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoControlador {

	private final ServicioProducto servicioProducto;

	@GetMapping("/producto")
	public ResponseEntity<List<Producto>> obtenerTodos() {
		List<Producto> productos = servicioProducto.buscarTodos();
		if (productos.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(productos);
		}
	}

	@GetMapping("/producto/{id}")
	public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
		Producto producto = servicioProducto.buscarPorId(id);
		if (producto == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(producto);
		}
	}

	@PostMapping("/producto")
	public ResponseEntity<Producto> nuevoProducto(@RequestBody Producto producto) {
		producto = servicioProducto.guardar(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(producto);
	}

	@PutMapping("/producto/{id}")
	public ResponseEntity<Producto> editarProducto(@RequestBody Producto producto, @PathVariable Long id) {
		if (servicioProducto.existePorId(id)) {
			producto.setId(id);
			return ResponseEntity.ok(servicioProducto.guardar(producto));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<Producto> borrarProducto(@PathVariable Long id) {
		if (servicioProducto.existePorId(id)) {
			servicioProducto.borrar(servicioProducto.buscarPorId(id));
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
