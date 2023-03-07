package com.example.udemywebflux.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.udemywebflux.dto.Multiply;
import com.example.udemywebflux.dto.Response0;
import com.example.udemywebflux.exception.CustomException;
import com.example.udemywebflux.service.SetupService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("memoSetup")
public class MemoSetupController {
    
    @Autowired
    SetupService setupService;

    @GetMapping("square/{input}")
    public Response0 square(@PathVariable int input){
        return setupService.square(input);
    }

    @GetMapping("multiply/{mul}")
    public List<Response0> multiply(@PathVariable int mul){
        return setupService.multiply(mul);
    }

    @GetMapping("reactive/square/{input}")
    public Mono<Response0> squareMono(@PathVariable int input){
        return setupService.squareMono(input);
    }

    // notice that value is printed out immediately
    // if I close the browser, setupService will stop processing immediately
    @GetMapping("reactive/multiply/{mul}")
    public Flux<Response0> multiplyFlux(@PathVariable int mul){
        return setupService.multiplyFlux(mul);
    }

    // notice the different between this and the above
    @GetMapping(value = "reactive/multiply/{mul}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response0> multiplyFluxStream(@PathVariable int mul){
        return setupService.multiplyFlux(mul);
    }

    @PostMapping("reactive/multiplyTwo")
    public Mono<Response0> multiplyTwo(@RequestBody Mono<Multiply> multiplyMono /* it can receive just Multiply as well. No need to be Mono */){
        /*
        curl --location 'http://localhost:8080/memoSetup/reactive/multiplyTwo' \
            --header 'Content-Type: application/json' \
            --data '{
                "a":5,
                "b":10
            }' 
         */
        return setupService.multiply(multiplyMono);
    }

    @GetMapping("reactive/testException/{input}")
    public Mono<Response0> testException(@PathVariable int input){
        return setupService.testException(input);
    }

    // can also throw Exception as part of the pipeline
    @GetMapping("reactive/testException2/{input}")
    public Mono<Response0> testException2(@PathVariable int input){
        return Mono.just(input)
                .handle((i, sink) -> {
                    if(i < 10) sink.error(new CustomException(100, "argument not valid"));
                    sink.next(i);
                })
                .cast(Integer.class)
                .flatMap(i -> setupService.testException(i));
    }
}
