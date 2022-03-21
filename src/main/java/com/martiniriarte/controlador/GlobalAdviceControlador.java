package com.martiniriarte.controlador;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.martiniriarte.error.ApiError;
import com.martiniriarte.error.exceptions.BuscarProductoSinResultadoException;
import com.martiniriarte.error.exceptions.CrearLoteException;
import com.martiniriarte.error.exceptions.LoteNoEncontradoException;
import com.martiniriarte.error.exceptions.NuevoUsuarioConDiferenteContrasenaException;
import com.martiniriarte.error.exceptions.PaginaNoEncontradaExeption;
import com.martiniriarte.error.exceptions.PedidoNoEncontradoExcepcion;
import com.martiniriarte.error.exceptions.ProductoNoEncontradoException;
import com.martiniriarte.error.exceptions.UsuariosNoEncontradosException;

@RestControllerAdvice
public class GlobalAdviceControlador extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ProductoNoEncontradoException.class, BuscarProductoSinResultadoException.class,
			PedidoNoEncontradoExcepcion.class, PaginaNoEncontradaExeption.class, UsuariosNoEncontradosException.class,
			LoteNoEncontradoException.class, UsuariosNoEncontradosException.class})
	public ResponseEntity<ApiError> handleNoEncontrado(ProductoNoEncontradoException exception) {
		ApiError apiError = ApiError.builder().estado(HttpStatus.NOT_FOUND).mensaje(exception.getMessage()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@ExceptionHandler({NuevoUsuarioConDiferenteContrasenaException.class, CrearLoteException.class})
	public ResponseEntity<ApiError> handleNeNuevoUsuarioError(Exception ex) {
		return construirErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(status, ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}

	private ResponseEntity<ApiError> construirErrorResponseEntity(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(ApiError.builder()
															.estado(status)
															.mensaje(message)
															.build());
	}

}
