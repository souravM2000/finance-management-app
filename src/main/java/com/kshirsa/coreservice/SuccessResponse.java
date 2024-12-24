package com.kshirsa.coreservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {

    private Boolean success;
    private String message;
    private T data;
}
