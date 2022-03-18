package com.martiniriarte.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.martiniriarte.dto.DetalleProductoDto;
import com.martiniriarte.dto.EditarProductoDto;
import com.martiniriarte.dto.ProductoDto;
import com.martiniriarte.modelo.Producto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoDtoConverter {

	private final ModelMapper modelMapper;
	
	public DetalleProductoDto convertirADtoDetalle(Producto producto) {
		return modelMapper.map(producto, DetalleProductoDto.class);
	}

	public ProductoDto convertirADto(Producto producto) {
		return modelMapper.map(producto, ProductoDto.class);
	}
	
	public Producto convertirProductoDtoAProducto(ProductoDto productoDTO) {
		return modelMapper.map(productoDTO, Producto.class);
	}
	
	public Producto convertirEditarProductoDtoAProducto(EditarProductoDto editarProductoDTO) {
		return modelMapper.map(editarProductoDTO, Producto.class);
	}

	public Producto convertirDetalleProductoDtoAProducto(DetalleProductoDto detalleProductoDTO) {
		return modelMapper.map(detalleProductoDTO, Producto.class);
	}

	public List<ProductoDto> convertirAListDto(List<Producto> productos) {
		List<ProductoDto> productosDTO = new ArrayList<>();

		for (Producto producto : productos) {
			productosDTO.add(convertirADto(producto));
		}
		return productosDTO;
	}
}
