package com.jeperello.reactiveapi.config;

import com.jeperello.reactiveapi.model.Product;
import com.jeperello.reactiveapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
@RequiredArgsConstructor
@Slf4j // Para usar log.info
public class DataInitializer {

    private final ProductRepository repository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            log.info("Iniciando carga de datos en H2...");

            // Definimos datos iniciales
            Flux<Product> products = Flux.just(
                    new Product(null, "Laptop Gamer", 1500.0),
                    new Product(null, "Mouse Inalámbrico", 25.0),
                    new Product(null, "Monitor 4K", 400.0)
            );

            // Los guardamos de forma reactiva
            repository.deleteAll() // Limpiamos la tabla
                    .thenMany(products) // Luego insertamos los nuevos
                    .flatMap(repository::save) // Guardamos cada uno
                    .subscribe(
                            p -> log.info("Producto insertado: {}", p),
                            e -> log.error("Error al inicializar: {}", e.getMessage()),
                            () -> log.info("Inicialización completada exitosamente.")
                    );
        };
    }
}