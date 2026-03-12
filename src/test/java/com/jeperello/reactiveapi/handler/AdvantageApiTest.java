package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.dto.AdvantageDto;
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
class AdvantageApiTest {

    @Autowired
    private WebTestClient webTestClient; // El "cliente" reactivo para testear

    @Test
    @DisplayName("Debe emitir las ventajas reactivas correctamente")
    void testStreamAdvantages() {
        Flux<AdvantageDto> result = webTestClient.get()
                .uri("/api/advantages")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(AdvantageDto.class)
                .getResponseBody();

        // El StepVerifier permite validar el flujo elemento por elemento
        StepVerifier.create(result)
                .expectNextMatches(t -> t.description().contains("Event Loop de Netty"))
                .expectNextCount(4) // Esperamos las 4 ventajas
                .verifyComplete();
    }
}
