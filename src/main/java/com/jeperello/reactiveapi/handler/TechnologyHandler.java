package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.dto.TechnologyDto;
import com.jeperello.reactiveapi.model.Technology;
import com.jeperello.reactiveapi.repository.TechnologyRepository;
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
public class TechnologyHandler {

    private final TechnologyRepository repositoryTech;

    public Mono<ServerResponse> streamTechnologies(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        repositoryTech.findAll()
                                .delayElements(Duration.ofSeconds(2))
                                .map(tech -> new TechnologyDto(tech.getDescription()))
                                .doOnNext(tech -> log.info(">> Enviando tecnología: {}", tech.description())) // Log de cada elemento
                                .doOnComplete(() -> log.info("✅ Stream de tecnologías finalizado con éxito.")),
                        TechnologyDto.class
                );
    }
}
