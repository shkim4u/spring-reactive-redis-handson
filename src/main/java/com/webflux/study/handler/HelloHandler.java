package com.webflux.study.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class HelloHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(fromValue("Hello " + request.pathVariable("name")));
    }

    /*
    // 세미나 코드 (위와 동일한 내용)
    HandlerFunction helloHandler = req ->
            ok().body(fromValue("Hello" + req.pathVariable("name"))); // 세미나 코드에서 사용한 fromObject 가 @Deprecated -> fromValue
    */
}
