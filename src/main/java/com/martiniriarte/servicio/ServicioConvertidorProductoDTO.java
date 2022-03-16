package com.martiniriarte.servicio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.martiniriarte.dto.DetalleProductoDTO;
import com.martiniriarte.dto.EditarProductoDTO;
import com.martiniriarte.dto.ProductoDTO;
import com.martiniriarte.modelo.Producto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioConvertidorProductoDTO {

	private final ModelMapper modelMapper;
	
	public DetalleProductoDTO convertirADtoDetalle(Producto producto) {
		return modelMapper.map(producto, DetalleProductoDTO.class);
	}

	public ProductoDTO convertirADto(Producto producto) {
		return modelMapper.map(producto, ProductoDTO.class);
	}
	
	public Producto convertirProductoDtoAProducto(ProductoDTO productoDTO) {
		return modelMapper.map(productoDTO, Producto.class);
	}
	
	public Producto convertirEditarProductoDtoAProducto(EditarProductoDTO editarProductoDTO) {
		return modelMapper.map(editarProductoDTO, Producto.class);
	}

	public Producto convertirDetalleProductoDtoAProducto(DetalleProductoDTO detalleProductoDTO) {
		return modelMapper.map(detalleProductoDTO, Producto.class);
	}

	public List<ProductoDTO> convertirAListDto(List<Producto> productos) {
		List<ProductoDTO> productosDTO = new ArrayList<>();

		for (Producto producto : productos) {
			productosDTO.add(convertirADto(producto));
		}
		return productosDTO;
	}
	
	public List<DetalleProductoDTO> convertirADtoConLombok(List<Producto> productos){
			
		List<DetalleProductoDTO> productosDTO = new ArrayList<>();

		for (Producto producto : productos) {
			productosDTO.add(DetalleProductoDTO.builder()
											.id(producto.getId())
											.nombre(producto.getNombre())
											.precio(producto.getPrecio())
											.categoriaNombre(producto.getCategoria().getNombre())
											.imagen(producto.getImagen())
											.build());
		}
		return productosDTO;		
	}
}
