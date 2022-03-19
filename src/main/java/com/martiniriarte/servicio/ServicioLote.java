package com.martiniriarte.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.martiniriarte.dto.CrearLoteDto;
import com.martiniriarte.error.exceptions.CrearLoteException;
import com.martiniriarte.modelo.Lote;
import com.martiniriarte.repositorio.RepositorioLote;
import com.martiniriarte.servicio.base.ServicioBase;
import com.martiniriarte.util.converter.LoteDtoConverter;

@Service
public class ServicioLote extends ServicioBase<Lote, Long, RepositorioLote> {

	private final ServicioProducto productoServicio;
	private LoteDtoConverter converter;

	@Autowired
	public ServicioLote(RepositorioLote repositorio, ServicioProducto servicioProducto, LoteDtoConverter converter) {
		super(repositorio);
		this.productoServicio = servicioProducto;
		this.converter = converter;
	}

	@Override
	public Optional<Lote> buscarPorId(Long id) {
		return repositorio.findByIdJoinFetch(id);
	}

	public Lote nuevoLote(CrearLoteDto nuevoLote) {
		
		Lote lote = converter.convertirCrearLoteDtoALote(nuevoLote);
		
		nuevoLote.getProductos().stream()
			.map(id -> 
				productoServicio.findByIdConLotes(id).orElseThrow(CrearLoteException::new)
			)
			.forEach(lote::agregarProducto);
			
		return guardar(lote);
	}

}
