package com.example.tienda101.productos.infrastructure;

import java.util.List;
import org.springframework.web.client.RestTemplate;
import com.example.tienda101.productos.domain.Producto;
import com.example.tienda101.productos.domain.ProductoDataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductoDataServiceRest implements ProductoDataService {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    public ProductoDataServiceRest(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Producto> findAll() {
        try {
            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, new TypeReference<List<Producto>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los productos desde el API REST", e);
        }
    }
}
