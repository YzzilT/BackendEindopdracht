package com.example.backendeindopdracht.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException() {super();}

    public RecordNotFoundException(String message) {
        super(message);
    }


}
