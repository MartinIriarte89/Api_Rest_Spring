package com.martiniriarte;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.martiniriarte.servicio.ServicioAlmacenamiento;

@SpringBootApplication
public class ApiRestSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ServicioAlmacenamiento servicioAlmacenamiento) {
		return args -> {
			servicioAlmacenamiento.deleteAll();
			servicioAlmacenamiento.init();
		};
	}

}
