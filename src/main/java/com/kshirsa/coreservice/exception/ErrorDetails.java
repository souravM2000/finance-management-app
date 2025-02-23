package com.kshirsa.coreservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetails {

    private String errorMessage;
    private String errorDetails;
    private Integer errorCode;
}
