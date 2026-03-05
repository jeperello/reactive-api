package com.jeperello.reactiveapi.handler;

import com.jeperello.reactiveapi.model.Product;
import com.jeperello.reactiveapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductRepository repository;

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll(), Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return repository.findById(id)
                .flatMap(product -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}