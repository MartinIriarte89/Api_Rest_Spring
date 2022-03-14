package com.martiniriarte.controlador;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.martiniriarte.error.ApiError;
import com.martiniriarte.error.BuscarProductoSinResultadoException;
import com.martiniriarte.error.PaginaNoEncontradaExeption;
import com.martiniriarte.error.ProductoNoEncontradoException;

@RestControllerAdvice
public class GlobalErrorControlador extends ResponseEntityExceptionHandler{

	@ExceptionHandler({ProductoNoEncontradoException.class, BuscarProductoSinResultadoException.class})
	public ResponseEntity<ApiError> handleProductoNoEncontrado(ProductoNoEncontradoException exception) {
		ApiError apiError = ApiError.builder().estado(HttpStatus.NOT_FOUND)
											  .mensaje(exception.getMessage())
											  .build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(PaginaNoEncontradaExeption.class)
	public ResponseEntity<ApiError> handlePaginaNoEncontrada(PaginaNoEncontradaExeption exception) {
		ApiError apiError = ApiError.builder().estado(HttpStatus.NOT_FOUND)
											  .mensaje(exception.getMessage())
											  .build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = ApiError.builder().estado(status)
											  .mensaje(ex.getMessage())
											  .build();
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
	
}
