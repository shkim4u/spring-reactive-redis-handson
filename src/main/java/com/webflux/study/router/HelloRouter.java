package com.webflux.study.router;

import com.webflux.study.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> route(HelloHandler helloHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloHandler::hello);
    }
}
