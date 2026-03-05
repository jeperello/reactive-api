package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.model.Product;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductApiTest {

    @Autowired
    private WebTestClient webTestClient; // El "cliente" reactivo para testear

    @Test
    @DisplayName("Debe listar todos los productos correctamente")
    void testGetAllProducts() {
        webTestClient.get()
                .uri("/api/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Product.class)
                .hasSize(3); // Insertamos 3 en el DataInitializer
    }

    @Test
    @DisplayName("Debe obtener un producto específico por ID")
    void testGetProductById() {
        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Laptop Gamer")
                .jsonPath("$.price").isEqualTo(1500.0);
    }

    @Test
    @DisplayName("Debe devolver 404 si el producto no existe")
    void testGetProductNotFound() {
        webTestClient.get()
                .uri("/api/products/99")
                .exchange()
                .expectStatus().isNotFound();
    }
}