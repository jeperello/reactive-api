package com.jeperello.reactiveapi.router;

import com.jeperello.reactiveapi.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler) {
        return RouterFunctions
                .route(GET("/api/products"), handler::getAllProducts)
                .andRoute(GET("/api/products/{id}"), handler::getProductById);
    }
}