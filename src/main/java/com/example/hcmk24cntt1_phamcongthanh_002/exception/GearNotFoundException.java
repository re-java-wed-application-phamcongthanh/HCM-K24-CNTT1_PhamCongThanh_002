package com.example.hcmk24cntt1_phamcongthanh_002.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GearNotFoundException extends RuntimeException{

    public GearNotFoundException(String message){
        super(message);
    }
}

