package com.jeperello.reactiveapi.repository;

import com.jeperello.reactiveapi.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    // Al extender de ReactiveCrudRepository, ya tenemos métodos que devuelven Flux y Mono
}
