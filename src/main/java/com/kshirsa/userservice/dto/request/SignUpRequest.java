package com.kshirsa.userservice.dto.request;

import com.kshirsa.userservice.UserConstants;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record SignUpRequest(String name,
                            @Pattern(regexp=UserConstants.PHONE_NUMBER_REGEX, message = "INVALID_MOBILE") String phoneNumber,
                            String countryCode,
                            String country,
                            String gender,
                            LocalDate dob) {
}
