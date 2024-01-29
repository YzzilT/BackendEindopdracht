package com.example.backendeindopdracht.exceptions;


import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException() {super();}

    public RecordNotFoundException(String message) {
        super(message);
    }


}
