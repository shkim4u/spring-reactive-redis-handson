package com.webflux.study.web_client;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HelloWebClient {

    private WebClient client = WebClient.create("http://localhost:8080");

    Mono<Object> result = client.get().uri("/hello/JongMin")
            .accept(MediaType.TEXT_PLAIN).exchangeToMono(res -> {
                if (res.statusCode().equals(HttpStatus.OK)) {
                    return res.bodyToMono(String.class);
                }else {
                    return res.createException().flatMap(Mono::error);
                }
            });



    public String getResult() {
        return ">> result = " + result.block();
    }
}
