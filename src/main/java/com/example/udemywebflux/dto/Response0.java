package com.example.udemywebflux.dto;

import java.util.Date;

public record Response0(Date date, int output) {
    public Response0(int output){
        this(new Date(), output);
    }
}
