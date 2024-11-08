package com.example.tienda101.productos.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tienda101.productos.domain.Producto;
import com.example.tienda101.productos.domain.ProductoDataService;
import com.example.tienda101.productos.domain.ProductoRepository;

@Service
public class ProductoService {

	private final ProductoRepository productoRepository;
	private final ProductoDataService productoDataService;

	public ProductoService(ProductoRepository productoRepository, ProductoDataService productoDataService) {
		this.productoRepository = productoRepository;
		this.productoDataService = productoDataService;
	}

	public List<Producto> getItems() {
		return productoDataService.findAll();
	}

	public Optional<Producto> getItemById(Long id) {
		return productoRepository.findById(id);
	}

	public Producto createItem(Producto producto) {
		return productoRepository.save(producto);
	}

	public Producto updateItem(Long id, Producto producto) {
		Producto found = productoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
		if (producto.getNombre() != null) {
			found.setNombre(producto.getNombre());
		}
		if (producto.getCosto() != null) {
			found.setCosto(producto.getCosto());
		}
		if (producto.getPrecio() != null) {
			found.setPrecio(producto.getPrecio());
		}
		if (producto.getCantidad() != null) {
			found.setCantidad(producto.getCantidad());
		}
		return productoRepository.save(found);
	}

	public void deleteItemById(Long id) {
		if (!productoRepository.existsById(id)) {
	        throw new RuntimeException("Producto con ID " + id + " no encontrado");
	    }
	    productoRepository.deleteById(id);
	}
}
