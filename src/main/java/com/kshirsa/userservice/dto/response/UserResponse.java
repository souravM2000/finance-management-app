package com.kshirsa.userservice.dto.response;

public record UserResponse(Integer userId, String userEmail, String userName, String userPhone, String phoneNumber,
                   String countryCode, String gender, String dob, String createdOn) {
}
