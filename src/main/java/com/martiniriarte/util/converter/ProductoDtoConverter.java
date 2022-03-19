package com.martiniriarte.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.martiniriarte.dto.GetProductoDto;
import com.martiniriarte.dto.EditarProductoDto;
import com.martiniriarte.dto.CrearProductoDto;
import com.martiniriarte.modelo.Producto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoDtoConverter {

	private final ModelMapper modelMapper;
	
	public GetProductoDto convertirProductoAProductoDto(Producto producto) {
		return modelMapper.map(producto, GetProductoDto.class);
	}

	public CrearProductoDto convertirProductoACrearProductoDto(Producto producto) {
		return modelMapper.map(producto, CrearProductoDto.class);
	}
	
	public EditarProductoDto convertirProductoAEditarProductoDto(Producto producto) {
		return modelMapper.map(producto, EditarProductoDto.class);
	}
	
	public Producto convertirCrearProductoDtoAProducto(CrearProductoDto crearProductoDto) {
		return modelMapper.map(crearProductoDto, Producto.class);
	}
	
	public Producto convertirEditarProductoDtoAProducto(EditarProductoDto editarProductoDTO) {
		return modelMapper.map(editarProductoDTO, Producto.class);
	}

	public Producto convertirProductoDtoAProducto(GetProductoDto productoDto) {
		return modelMapper.map(productoDto, Producto.class);
	}

	public List<GetProductoDto> convertirAListDto(List<Producto> productos) {
		List<GetProductoDto> productosDTO = new ArrayList<>();

		for (Producto producto : productos) {
			productosDTO.add(convertirProductoAProductoDto(producto));
		}
		return productosDTO;
	}
}
