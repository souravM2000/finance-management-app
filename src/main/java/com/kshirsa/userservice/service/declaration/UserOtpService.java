package com.kshirsa.userservice.service.declaration;

import com.kshirsa.userservice.exception.UserException;

public interface UserOtpService {

    public Boolean validateEmail(String email) throws UserException;
    public String generateOtp(String email);
}
