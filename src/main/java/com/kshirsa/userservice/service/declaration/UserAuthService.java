package com.kshirsa.userservice.service.declaration;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.userservice.dto.request.OtpValidateRequest;
import com.kshirsa.userservice.dto.response.LoginResponse;
import com.kshirsa.userservice.dto.response.NewTokenResponse;
import lombok.NonNull;

public interface UserAuthService {
    LoginResponse otpValidateFlow(OtpValidateRequest request) throws CustomException;

    NewTokenResponse refreshToken(@NonNull String token) throws CustomException;

    Boolean logout(@NonNull String token);
}
