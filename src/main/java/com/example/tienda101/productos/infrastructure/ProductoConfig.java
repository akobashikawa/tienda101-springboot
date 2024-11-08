package com.example.tienda101.productos.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import com.example.tienda101.productos.domain.ProductoDataService;
import com.example.tienda101.productos.domain.ProductoRepository;

@Configuration
public class ProductoConfig {
	
	private final ProductoRepository productoRepository;
	
	public ProductoConfig(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
//	@Bean
//	@ConditionalOnMissingBean(ProductoDataService.class)
//	public ProductoDataService defaultProductoDataService() {
//	    return new ProductoDataServiceDatabase(productoRepository);
//	}
	
    @Bean
    @Profile("json")
    public ProductoDataService productoDataServiceJson() {
        return new ProductoDataServiceJson("src/main/resources/productos.json");
    }

    @Bean
    @Profile("database")
    public ProductoDataService productoDataServiceDatabase() {
		return new ProductoDataServiceDatabase(productoRepository);
    }

    @Bean
    @Profile("rest")
    public ProductoDataService productoDataServiceRest(RestTemplate restTemplate) {
        return new ProductoDataServiceRest("http://localhost:8081/api/productos", restTemplate);
    }

}

