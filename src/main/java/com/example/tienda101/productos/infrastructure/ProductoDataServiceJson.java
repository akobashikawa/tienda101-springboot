package com.example.tienda101.productos.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.example.tienda101.productos.domain.Producto;
import com.example.tienda101.productos.domain.ProductoDataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductoDataServiceJson implements ProductoDataService {

    private final String jsonFilePath;

    public ProductoDataServiceJson(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }

    @Override
    public List<Producto> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            return objectMapper.readValue(jsonContent, new TypeReference<List<Producto>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }
}
