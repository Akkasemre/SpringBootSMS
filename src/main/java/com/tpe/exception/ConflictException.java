package com.tpe.exception;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        //super conflict exception o parent class dan extend edince onun ismini buraya koy der.
        super(message);
    }
}
