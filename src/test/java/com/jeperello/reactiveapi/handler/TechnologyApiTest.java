package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.dto.TechnologyDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TechnologyApiTest {

    @Autowired
    private WebTestClient webTestClient; // El "cliente" reactivo para testear

    @Test
    @DisplayName("Debe recibir un flujo de tecnologías con delay")
    void testStreamTechnologies() {
        Flux<TechnologyDto> result = webTestClient.get()
                .uri("/api/technologies")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(TechnologyDto.class)
                .getResponseBody();

        // El StepVerifier permite validar el flujo elemento por elemento
        StepVerifier.create(result)
                .expectNextMatches(t -> t.description().contains("API REST"))
                .expectNextCount(16) // Esperamos las 16 tecnologías
                .verifyComplete();
    }
}
