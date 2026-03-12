package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.dto.TechnologyDto;
import com.jeperello.reactiveapi.repository.AdvantageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdvantageHandler {

    private final AdvantageRepository advantageRepository;

    public Mono<ServerResponse> streamAdvantages(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        advantageRepository.findAll()
                                .delayElements(Duration.ofSeconds(3))
                                .map(advantage -> new TechnologyDto(advantage.getDescription()))
                                .doOnNext(advantage -> log.info(">> Enviando tecnología: {}", advantage.description())) // Log de cada elemento
                                .doOnComplete(() -> log.info("✅ Stream de ventajas finalizado con éxito.")),
                        TechnologyDto.class
                );
    }
}