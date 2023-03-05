package com.example.udemywebflux.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.udemywebflux.dto.Response0;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.udemywebflux.util.Util.*;

@Service
public class SetupService {
    
    public Response0 square(int i){
        return new Response0(i * i);
    }

    public Mono<Response0> squareMono(int i){
        return Mono.fromSupplier(() -> i * i)
                    .map(x -> new Response0(x));
    }

    public List<Response0> multiply(int mul){
        return IntStream.range(0, 10)
                .peek(i -> sleep(1))
                .peek(i -> println("processing: " + i))
                .mapToObj(i -> new Response0(mul * i))
                .collect(Collectors.toList());
    }

    public Flux<Response0> multiplyFlux(int mul){
        return Flux.range(0, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(x -> println("processing: " + x))
                .map( x -> new Response0(mul * x));
    }
}
