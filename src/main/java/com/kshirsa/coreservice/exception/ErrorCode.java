package com.kshirsa.coreservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GENERAL_EXCEPTION(500, 100, "Something went wrong"),

    USER_NOT_FOUND(404, 111, "UserDetails with this email id does not exist."),
    EMAIL_ALREADY_EXISTS(422, 116, "Email Id already in use. Try with another email."),

    REFRESH_TOKEN_NOT_FOUND(404, 122, "Refresh token not found."),
    REFRESH_TOKEN_EXPIRED(403, 123, "Refresh Token is Expired."),
    JWT_EXPIRED(401, 125, "Provided JWT is Expired."),
    JWT_WRONG_SIGNATURE(401, 126, "Provided JWT signature is not valid. Please provide valid JWT."),

    INVALID_OTP(400, 128, "OTP invalid or expired."),
    OTP_VALIDATION_EXPIRED(400, 129, "OTP validation time expired. Please try again."),
    INVALID_USER_ID(400, 130, "Given user id is not valid."),

    INVALID_MOBILE(400, 133, "Given mobile number is in invalid format."),
    INVALID_EMAIL(400, 134, "Given email is in invalid format."),

    EMAIL_NOT_BLANK(400, 140, "Email can not be blank"),
    PASSWORD_NOT_BLANK(400, 141, "Password can not be blank"),

    DISPOSABLE_EMAIL(400, 142, "Disposable email is not allowed"),
    INVALID_EMAIL_DOMAIN(400, 143, "Invalid email domain");

    private final int httpStatusCode;
    private final int errorCode;
    private final String errorMessage;
}
