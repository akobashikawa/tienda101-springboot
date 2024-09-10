package com.example.tienda101.ventas.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda101.personas.application.PersonaService;
import com.example.tienda101.personas.domain.Persona;
import com.example.tienda101.productos.application.ProductoService;
import com.example.tienda101.productos.domain.Producto;
import com.example.tienda101.ventas.domain.Venta;
import com.example.tienda101.ventas.domain.VentaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

	private final VentaRepository ventaRepository;
    
	private final ProductoService productoService;
    private final PersonaService personaService;
    
    public VentaService(VentaRepository ventaRepository, ProductoService productoService, PersonaService personaService) {
        this.ventaRepository = ventaRepository;
        this.productoService = productoService;
        this.personaService = personaService;
    }

    public List<Venta> getItems() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> getItemById(Long id) {
        return ventaRepository.findById(id);
    }
    
    public Venta createItem(VentaDTO ventaDTO) {
    	Venta venta = new Venta();
        Producto producto = productoService.getItemById(ventaDTO.getProducto_id())
    			.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
		Persona persona = personaService.getItemById(ventaDTO.getPersona_id())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
		
//    	int nuevaCantidad = producto.getCantidad() - ventaDTO.getCantidad();
//    	producto.setCantidad(nuevaCantidad);
//    	productoService.updateItem(producto.getId(), producto);
    	decProductoCantidad(producto, ventaDTO.getCantidad());
    	
    	venta.setProducto(producto);
    	venta.setPersona(persona);
    	venta.setPrecio(ventaDTO.getPrecio());
    	venta.setCantidad(ventaDTO.getCantidad());
    	venta.setFechaHora(LocalDateTime.now());
    	
    	return ventaRepository.save(venta);
    }

    public Venta updateItem(Long id, VentaDTO ventaDTO) {
        Venta venta = this.getItemById(id)
        		.orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        Producto producto = productoService.getItemById(ventaDTO.getProducto_id())
    			.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
		Persona persona = personaService.getItemById(ventaDTO.getPersona_id())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    	
//    	int diferencia = ventaDTO.getCantidad() - venta.getCantidad();
//    	int nuevaCantidad = producto.getCantidad() - diferencia;
//    	producto.setCantidad(nuevaCantidad);
//    	productoService.updateItem(producto.getId(), producto);
    	decProductoCantidad(producto, ventaDTO.getCantidad(), venta.getCantidad());
    	
    	venta.setProducto(producto);
    	venta.setPersona(persona);
    	
    	if (ventaDTO.getPrecio() != null) {
    		venta.setPrecio(ventaDTO.getPrecio());
    	}
    	if (ventaDTO.getCantidad() != null) {
    		venta.setCantidad(ventaDTO.getCantidad());
    	}
    	venta.setFechaHora(LocalDateTime.now());
    	
    	return ventaRepository.save(venta);
    }

    public void deleteItemById(Long id) {
        ventaRepository.deleteById(id);
    }
    
    private void decProductoCantidad(Producto producto, int cantidad, int cantidadAnterior) {
        if (producto.getCantidad() < cantidad) {
            throw new RuntimeException("Cantidad insuficiente para el producto " + producto.getId());
        }
        
        int diferencia = cantidad - cantidadAnterior;
        int nuevaCantidad = producto.getCantidad() - diferencia;
        producto.setCantidad(nuevaCantidad);
        productoService.updateItem(producto.getId(), producto);
    }
    
    private void decProductoCantidad(Producto producto, int cantidad) {
    	decProductoCantidad(producto, cantidad, 0);
    }
}
