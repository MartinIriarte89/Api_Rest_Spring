package com.martiniriarte.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ApiError {

	@NonNull
	private HttpStatus estado;

	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime fecha = LocalDateTime.now();

	@NonNull
	private String mensaje;
}
