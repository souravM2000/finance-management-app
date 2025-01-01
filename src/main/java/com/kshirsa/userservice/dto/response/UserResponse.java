package com.kshirsa.userservice.dto.response;

import com.kshirsa.userservice.entity.UserDetails;

public record UserResponse(UserDetails userDetails, Boolean isSignUpFlowCompleted) {
}
