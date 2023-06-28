package com.webflux.study.router;

import com.webflux.study.handler.BasicHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BasicRouter {

    private final BasicHandler basicHandler;

    @Bean
    public RouterFunction<ServerResponse> basicRoute() {
        return RouterFunctions.route()
                .GET("/reactive-list", serverRequest -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(basicHandler.findReactorList(), String.class))
                .GET("/normal-list", serverRequest -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(basicHandler.findNormalList(), String.class))
                .GET("/load", serverRequest -> { basicHandler.loadData(); return ServerResponse.ok()
                        .body(BodyInserters.fromValue("Load Data Completed")); })
                .GET("/clear", serverRequest -> { basicHandler.clearData(); return ServerResponse.ok()
                        .body(BodyInserters.fromValue("Data Cleared")); })
                .build();
    }
}
