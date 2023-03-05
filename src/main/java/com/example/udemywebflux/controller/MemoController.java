package com.example.udemywebflux.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.udemywebflux.dto.Response0;
import com.example.udemywebflux.service.SetupService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("memo")
public class MemoController {
    
    @Autowired
    SetupService setupService;

    @GetMapping("setup/square/{input}")
    public Response0 square(@PathVariable int input){
        return setupService.square(input);
    }

    @GetMapping("setup/multiply/{mul}")
    public List<Response0> multiply(@PathVariable int mul){
        return setupService.multiply(mul);
    }

    @GetMapping("setup/reactive/square/{input}")
    public Mono<Response0> squareMono(@PathVariable int input){
        return setupService.squareMono(input);
    }

    // notice that value is printed out immediately
    // if I close the browser, setupService will stop processing immediately
    @GetMapping("setup/reactive/multiply/{mul}")
    public Flux<Response0> multiplyFlux(@PathVariable int mul){
        return setupService.multiplyFlux(mul);
    }

    // notice the different between this and the above
    @GetMapping(value = "setup/reactive/multiply/{mul}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response0> multiplyFluxStream(@PathVariable int mul){
        return setupService.multiplyFlux(mul);
    }
}
