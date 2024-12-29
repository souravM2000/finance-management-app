package com.kshirsa.coreservice;

import com.kshirsa.coreservice.exception.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureResponse {
    private Boolean success;
    private String message;
    private ErrorDetails errorDetails;
}
