package com.kshirsa.userservice.dto.request;

public record OtpValidateRequest(String email, int otp) {
}
