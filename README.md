# 🚀 Reactive Product & Tech API - Spring Boot WebFlux

Esta es una API REST completamente **reactiva** y **no bloqueante** desarrollada con **Spring Boot 3.x** y **Java 21**. El proyecto demuestra estándares de arquitectura moderna, persistencia asíncrona mediante R2DBC y procesamiento de flujos de datos en tiempo real (Streaming).

## 🛠️ Tecnologías y Stack Técnico

- **Java 21 (LTS)**: Uso de **Records** y optimizaciones de rendimiento de última generación.
- **Spring WebFlux**: Framework reactivo basado en el motor de eventos de Project Reactor.
- **Spring Data R2DBC**: Persistencia relacional asíncrona (Non-blocking SQL).
- **H2 Database**: Base de datos en memoria configurada con drivers reactivos.
- **Lombok & SLF4J**: Gestión eficiente de boilerplate y logging trazable por hilos.
- **JUnit 5, WebTestClient & StepVerifier**: Suite de tests para flujos asíncronos y streams.
- **Docker**: Empaquetado profesional mediante *Multi-stage builds* (Alpine-based).
- **GitHub Actions**: Pipeline de CI (Integración Continua) que valida tests y construcción de imagen.

## 🏗️ Arquitectura de Software

El proyecto implementa el enfoque **Funcional** de WebFlux, separando la definición de rutas de la lógica de negocio:

1. **Router (`ProductRouter`)**: Definición centralizada y funcional de los endpoints.
2. **Handlers (`ProductHandler`, `TechnologyHandler`)**: Procesamiento asíncrono de peticiones.
3. **Repository**: Capa de datos reactiva que devuelve `Mono<T>` y `Flux<T>`.
4. **DTOs (Records)**: Estructuras de datos inmutables y ligeras para la exposición de la API.

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos
- JDK 21 (Configurar `JAVA_HOME` para evitar conflictos de versiones).
- Docker (opcional para ejecución en contenedores).

### Ejecución con Maven Wrapper
```bash
./mvnw clean spring-boot:run
