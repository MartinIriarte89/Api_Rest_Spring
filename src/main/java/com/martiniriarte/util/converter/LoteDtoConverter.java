package com.martiniriarte.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.martiniriarte.dto.CrearLoteDto;
import com.martiniriarte.modelo.Lote;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoteDtoConverter {

	private final ModelMapper mapper;
	
	public CrearLoteDto convertirLoteACrearLoteDto(Lote lote) {
		return mapper.map(lote, CrearLoteDto.class);
	}
	
	public Lote convertirCrearLoteDtoALote(CrearLoteDto crearLoteDto) {
		return mapper.map(crearLoteDto, Lote.class);
	}
	
}
