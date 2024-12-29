package com.kshirsa.userservice.dto.response;

import java.time.Instant;

public record NewTokenResponse(String jwtToken, Instant refreshTokenExpiryTime) {
}
