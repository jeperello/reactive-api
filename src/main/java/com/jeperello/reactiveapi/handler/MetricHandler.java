package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.dto.MemoryMetricDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class MetricHandler {

    public Mono<ServerResponse> getMemoryMetrics(ServerRequest request) {
        // Flux que emite cada 2 segundos indefinidamente
        Flux<MemoryMetricDto> metricsFlux = Flux.interval(Duration.ofSeconds(2))
                .map(tick -> {
                    Runtime runtime = Runtime.getRuntime();
                    long total = runtime.totalMemory() / (1024 * 1024);
                    long free = runtime.freeMemory() / (1024 * 1024);
                    return new MemoryMetricDto(total - free, free, total);
                });

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(metricsFlux, MemoryMetricDto.class);
    }
}