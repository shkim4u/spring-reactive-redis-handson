package com.webflux.study.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BasicHandler {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, String> reactiveRedisOperations;
    private final RedisTemplate<String , String> stringStringRedisTemplate;
    private static final AtomicInteger count = new AtomicInteger(0);

    public void loadData() {
        List<String> data = new ArrayList<>();
        IntStream.range(0, 100000).forEach(i -> data.add(UUID.randomUUID().toString()));

        Flux<String> stringFlux = Flux.fromIterable(data);

        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(stringFlux.flatMap(uid -> reactiveRedisOperations.opsForValue()
                        .set(String.valueOf(count.getAndAdd(1)), uid)))
                .subscribe();
    }

    /**
     * Both ReactiveRedisOperations and RedisTemplate are different implementations of the Redis client for use in Java applications.
     * The main difference between the two is the programming paradigm they follow.
     *
     * ReactiveRedisOperations are based on Reactive programming whereas RedisTemplate follows a more traditional imperative programming approach.
     * Reactive RedisOperations are designed to handle streams of data asynchronously, making them more suitable for large-scale reactive systems.
     *
     * They allow you to use reactive APIs, which provide non-blocking and event-driven programming paradigms.
     * This enables you to build reactive applications that are more efficient and scalable.
     *
     * On the other hand, RedisTemplate is designed to work with traditional Java-based applications.
     * It uses standard Java coding practices like object serialization to simplify your code.
     * RedisTemplate provides a more intuitive and easier-to-use interface for interacting with Redis.
     * Thus, you are working with a traditional Java-based application, RedisTemplate might be a better choice. However, if you are working with a reactive system, ReactiveRedisOperations might be more appropriate.
     * @return
     */
    public Flux<String> findReactorList() {
        return reactiveRedisOperations
                .keys("*")
                .flatMap(key -> reactiveRedisOperations.opsForValue().get(key));
    }

    public Flux<String> findNormalList() {
        return Flux.fromIterable(Objects.requireNonNull(stringStringRedisTemplate.keys("*"))
                .stream()
                .map(key -> stringStringRedisTemplate.opsForValue().get(key))
                .collect(Collectors.toList()));
    }

    public void clearData() {
        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .subscribe();
    }
}
