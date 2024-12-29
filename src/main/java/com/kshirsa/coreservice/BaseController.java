package com.kshirsa.coreservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public <T> ResponseEntity<SuccessResponse<T>> ok(T data, String message) {
        return new ResponseEntity<>(new SuccessResponse<>(true,message,data), HttpStatus.OK);
    }
}
