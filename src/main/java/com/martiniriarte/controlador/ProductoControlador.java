package com.martiniriarte.controlador;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.martiniriarte.dto.DetalleProductoDTO;
import com.martiniriarte.dto.ProductoDTO;
import com.martiniriarte.error.ApiError;
import com.martiniriarte.error.BuscarProductoSinResultadoException;
import com.martiniriarte.error.ProductoNoEncontradoException;
import com.martiniriarte.modelo.Producto;
import com.martiniriarte.servicio.ServicioAlmacenamiento;
import com.martiniriarte.servicio.ServicioConvertidorProductoDTO;
import com.martiniriarte.servicio.ServicioProducto;
import com.martiniriarte.util.paginacion.PaginacionLinks;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoControlador {

	private final ServicioProducto servicioProducto;
	private final ServicioConvertidorProductoDTO convertidor;
	private final ServicioAlmacenamiento servicioAlmacenamiento;
	private final PaginacionLinks paginacionLinks;

	/*
	@GetMapping(value = "/producto", params = "nombre")
	public ResponseEntity<List<DetalleProductoDTO>> obtenerTodosPorNombre(@RequestParam("nombre") String txt,
			@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request){
		
		Page<Producto> productos = servicioProducto.buscarPorNombre(txt, pageable);
		if (productos.isEmpty()) {
			throw new BuscarProductoSinResultadoException(txt);
		} else {

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginacionLinks.crearLinkHeader(productos, builder))
					.body(convertidor.convertirAListDto(productos.getContent()));
		}
	}*/
	
	@ApiOperation(value = "Obtener pagina de productos", notes = "Provee un mecanismo para obtener todos los productos con paginación, y tambien permite filtrar por nombre y precio dicha página")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = List.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@GetMapping("/producto")
	public ResponseEntity<List<DetalleProductoDTO>> obtenerTodosFiltrado(
			@ApiParam(value = "Cadena para filtrar por nombre del producto", required = false, type = "String") @RequestParam("nombre") Optional<String> nombre,
			@ApiParam(value = "Precio maximo para filtrar el producto", required = false, type = "float")@RequestParam("precio") Optional<Float> precio,
			@PageableDefault(size = 10, page = 0) Pageable pageable, HttpServletRequest request){
		
		Page<Producto> productos = servicioProducto.findByArgs(nombre, precio, pageable);
		
		if(productos.isEmpty()) {
			throw new BuscarProductoSinResultadoException();
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

		return ResponseEntity.ok().header("link", paginacionLinks.crearLinkHeader(productos, builder))
				.body(convertidor.convertirAListDto(productos.getContent()));
	}

	@ApiOperation(value = "Obtener un producto por su id", notes = "Provee un mecanismo para obtener todos los datos de un producto por su id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK", response = DetalleProductoDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@GetMapping("/producto/{id}")
	public ResponseEntity<DetalleProductoDTO> obtenerUno(
			@ApiParam(value = "Id del producto", required = true, type = "Long") @PathVariable Long id) {
		Producto producto = servicioProducto.buscarPorId(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
		return ResponseEntity.ok(convertidor.convertirADto(producto));
	}

	@ApiOperation(value = "Crear un producto", notes = "Provee un mecanismo para crear un nuevo producto")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = DetalleProductoDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@PostMapping(value = "/producto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DetalleProductoDTO> nuevoProducto(
			@ApiParam(value = "Json con los datos para crear un Producto", required = true, type = "productoDTO") @RequestPart("productoDTO") ProductoDTO productoDTO,
			@ApiParam(value = "Archivo de imagen", required = false, type = "MultipartFile") @RequestPart("file") MultipartFile file) {

		String urlImagen = null;

		if (!file.isEmpty()) {
			String imagen = servicioAlmacenamiento.store(file);
			urlImagen = MvcUriComponentsBuilder.fromMethodName(FicheroControlador.class, "serveFile", imagen, null)
					.build().toUriString();

		}

		productoDTO.setImagen(urlImagen);
		Producto producto = convertidor.convertirProductoDtoAProducto(productoDTO);
		producto = servicioProducto.guardar(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(convertidor.convertirADto(producto));
	}

	@ApiOperation(value = "Editar un producto por su id", notes = "Provee un mecanismo para editar todos los datos de un producto por su id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created", response = ProductoDTO.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@PutMapping("/producto/{id}")
	public ResponseEntity<ProductoDTO> editarProducto(
			@ApiParam(value = "Json para editar el producto", required = true, type = "productoDTO") @RequestBody ProductoDTO productoDTO,
			@ApiParam(value = "Id del producto a editar", required = true, type = "long") @PathVariable Long id) {
		if (!servicioProducto.existePorId(id)) {
			throw new ProductoNoEncontradoException(id);
		}
		Producto producto = convertidor.convertirProductoDtoAProducto(productoDTO);
		servicioProducto.guardar(producto);
		return ResponseEntity.ok(productoDTO);

	}

	@ApiOperation(value = "Eliminar un producto por su id", notes = "Provee un mecanismo para eliminar un producto por su id")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No Content", response = Producto.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<Producto> borrarProducto(
			@ApiParam(value = "Id del producto", required = true, type = "long") @PathVariable Long id) {
		Producto producto = servicioProducto.buscarPorId(id).orElseThrow(() -> new ProductoNoEncontradoException(id));
		servicioProducto.borrar(producto);
		return ResponseEntity.noContent().build();
	}
}
