package com.jeperello.reactiveapi.router;

import com.jeperello.reactiveapi.handler.ProductHandler;
import com.jeperello.reactiveapi.handler.TechnologyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler, TechnologyHandler techHandler) {
        return RouterFunctions
                // Redirección de la raíz a las tecnologías
                .route(GET("/"), request -> ServerResponse.temporaryRedirect(URI.create("/api/technologies")).build())

                // Rutas de Productos
                .andRoute(GET("/api/products"), handler::getAllProducts)
                .andRoute(GET("/api/products/{id}"), handler::getProductById)

                // Ruta de Tecnologías (Streaming)
                .andRoute(GET("/api/technologies"), techHandler::streamTechnologies);
    }
}