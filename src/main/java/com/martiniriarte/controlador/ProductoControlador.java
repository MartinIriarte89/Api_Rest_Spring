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

import com.martiniriarte.dto.DetalleProductoDTO;
import com.martiniriarte.dto.ProductoDTO;
import com.martiniriarte.error.ProductoNoEncontradoException;
import com.martiniriarte.modelo.Producto;
import com.martiniriarte.servicio.ServicioConvertidorProductoDTO;
import com.martiniriarte.servicio.ServicioProducto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoControlador {

	private final ServicioProducto servicioProducto;
	private final ServicioConvertidorProductoDTO convertidor;

	@GetMapping("/producto")
	public ResponseEntity<List<DetalleProductoDTO>> obtenerTodos() {
		List<Producto> productos = servicioProducto.buscarTodos();
		if (productos.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(convertidor.convertirAListDto(productos));
		}
	}

	@GetMapping("/producto/{id}")
	public ResponseEntity<DetalleProductoDTO> obtenerUno(@PathVariable Long id) {
		return ResponseEntity.ok(convertidor.convertirADto(servicioProducto.buscarPorId(id)));
		
	}

	@PostMapping("/producto")
	public ResponseEntity<DetalleProductoDTO> nuevoProducto(@RequestBody ProductoDTO crearProductoDTO) {
		Producto producto = convertidor.convertirProductoDtoAProducto(crearProductoDTO);
		producto = servicioProducto.guardar(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(convertidor.convertirADto(producto));
	}

	@PutMapping("/producto/{id}")
	public ResponseEntity<ProductoDTO> editarProducto(@RequestBody ProductoDTO productoDTO, @PathVariable Long id) {
		if (!servicioProducto.existePorId(id)) {
			throw new ProductoNoEncontradoException(id);
		}
		Producto producto = convertidor.convertirProductoDtoAProducto(productoDTO);
		servicioProducto.guardar(producto);
		return ResponseEntity.ok(productoDTO);

	}

	@DeleteMapping("/producto/{id}")
	public ResponseEntity<Producto> borrarProducto(@PathVariable Long id) {
		servicioProducto.borrar(servicioProducto.buscarPorId(id));
		return ResponseEntity.noContent().build();
	}
	
}
