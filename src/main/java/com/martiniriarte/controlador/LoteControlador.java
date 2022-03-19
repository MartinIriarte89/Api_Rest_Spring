package com.martiniriarte.controlador;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.martiniriarte.dto.CrearLoteDto;
import com.martiniriarte.error.exceptions.LoteNoEncontradoException;
import com.martiniriarte.modelo.Lote;
import com.martiniriarte.servicio.ServicioLote;
import com.martiniriarte.util.paginacion.PaginacionLinks;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lote")
@RequiredArgsConstructor
public class LoteControlador {

	private final ServicioLote servicioLote;
	private final PaginacionLinks paginacionLinks;

	@GetMapping("/")
	public ResponseEntity<List<Lote>> lotes(Pageable pageable, HttpServletRequest request)
			throws LoteNoEncontradoException {
		Page<Lote> result = servicioLote.buscarTodos(pageable);

		if (result.isEmpty()) {
			throw new LoteNoEncontradoException();
		} else {

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginacionLinks.crearLinkHeader(result, uriBuilder))
					.body(result.getContent());

		}
	}

	@PostMapping("/")
	public ResponseEntity<Lote> nuevoLote(@RequestBody CrearLoteDto nuevoLote) {
		Lote lote = servicioLote.nuevoLote(nuevoLote);

		return ResponseEntity.status(HttpStatus.CREATED).body(lote);
	}

}
