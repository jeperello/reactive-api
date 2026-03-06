package com.jeperello.reactiveapi.config;

import com.jeperello.reactiveapi.model.Product;
import com.jeperello.reactiveapi.model.Technology;
import com.jeperello.reactiveapi.repository.ProductRepository;
import com.jeperello.reactiveapi.repository.TechnologyRepository;
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
    private final TechnologyRepository repositoryTech;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            log.info("Iniciando carga de datos en H2...");
            Flux<Technology> techs = Flux.just(
                    new Technology(null, "Bienvenido y gracias por pasar por mi primera API REST reactiva."),
                    new Technology(null, "Paso a comentarte brevemente las tecnologias utilizadas:"),
                    new Technology(null, "✅ Java 21 (LTS): consigo trae mejoras de rendimiento."),
                    new Technology(null, "✅ Spring Boot 4.0.3, latest stable release."),
                    new Technology(null, "✅ Spring WebFlux: Framework basado en Project Reactor"),
                    new Technology(null, "✅ Spring Data R2DBC: Conectividad reactiva"),
                    new Technology(null, "✅ H2 Database: Base de datos en memoria"),
                    new Technology(null, "✅ Lombok: para reducción de boilerplate"),
                    new Technology(null, "✅ SLF4J: interfaz para logueo"),
                    new Technology(null, "✅ JUnit 5 & WebTestClient: Tests + Tests reactivos"),
                    new Technology(null, "✅ Docker: Multi-stage builds"),
                    new Technology(null, "✅ GitHub Actions: Pipeline de CI"),
                    new Technology(null, "¿Que he conseguido con este proyecto?"),
                    new Technology(null, "✅ Aprender a construir una API REST reactiva desde cero."),
                    new Technology(null, "✅ Mantenerme actualizado con las nuevas tecnologias para microservicios."),
                    new Technology(null, "✅ Mejorar mis habilidades como desarrollador back-end."),
                    new Technology(null, "Nuevamente gracias por pasar. Que tengas un gran dia!! :)")

                    );
            repositoryTech.saveAll(techs).subscribe(); // Inyecta el nuevo repo en el constructor
            log.info("Carga de tecnologias completada exitosamente.");

            // Definimos datos iniciales de productos de prueba
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