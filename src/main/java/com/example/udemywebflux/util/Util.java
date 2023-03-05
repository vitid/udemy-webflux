package com.example.udemywebflux.util;

public class Util {
    
    public static void sleep(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        } catch(Exception e){

        }
    }

    public static void println(String message){
        System.out.println(message);
    }
}
