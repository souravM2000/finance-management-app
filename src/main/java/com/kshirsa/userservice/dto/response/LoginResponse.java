package com.kshirsa.userservice.dto.response;

import com.kshirsa.userservice.entity.UserDetails;

import java.time.Instant;

public record LoginResponse(String accessToken,
                            String refreshToken,
                            Instant refreshTokenExpirationTime,
                            Boolean isSignUpFlowCompleted,
                            UserDetails user) {
}
