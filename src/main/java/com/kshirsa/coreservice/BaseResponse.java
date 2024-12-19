package com.kshirsa.coreservice;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class BaseResponse {

    private Boolean success;
    private String message;
    private Object data;
    private ErrorDetails errorDetails;
}
