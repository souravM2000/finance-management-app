package com.kshirsa.userservice.dto.request;

import com.kshirsa.userservice.UserConstants;
import jakarta.validation.constraints.Pattern;

public record OtpValidateRequest(@Pattern(regexp= UserConstants.EMAIL_REGEX, message = "INVALID_EMAIL_FORMAT") String email,
                                 int otp) {
}
