package com.kshirsa.coreservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse> customException(CustomException ex) {
        ErrorCode ec = ErrorCode.valueOf(ex.getMessage());
        ErrorDetails error = new ErrorDetails(ec.getErrorMessage(),null,ec.getErrorCode());
        return new ResponseEntity<>(new BaseResponse(false, ec.getErrorMessage(),null,error), HttpStatusCode.valueOf(ec.getHttpStatusCode()));
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> allOtherException(Exception ex) {
        log.error(ex.getMessage(),ex);
        ErrorDetails error = new ErrorDetails(ErrorCode.GENERAL_EXCEPTION.getErrorMessage(),
                ex.getMessage(), ErrorCode.GENERAL_EXCEPTION.getErrorCode());
        return new ResponseEntity<>(new BaseResponse(false, error.getErrorMessage(),null,error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
