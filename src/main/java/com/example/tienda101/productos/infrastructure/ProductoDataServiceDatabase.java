package com.example.tienda101.productos.infrastructure;

import java.util.List;

import com.example.tienda101.productos.domain.Producto;
import com.example.tienda101.productos.domain.ProductoDataService;
import com.example.tienda101.productos.domain.ProductoRepository;

public class ProductoDataServiceDatabase implements ProductoDataService {
	
	private final ProductoRepository productoRepository;
	
	public ProductoDataServiceDatabase(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

}
