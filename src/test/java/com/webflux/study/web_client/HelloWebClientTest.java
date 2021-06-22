package com.webflux.study.web_client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHello() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/hello/jong")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello jong");
    }
}