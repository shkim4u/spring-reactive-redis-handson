package com.webflux.study.router;

import com.webflux.study.handler.HelloHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class HelloRouter {

    private final HelloHandler helloHandler;

    @Bean
    public RouterFunction<ServerResponse> helloRoute() {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello/{name}")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), helloHandler::hello);
    }
}
