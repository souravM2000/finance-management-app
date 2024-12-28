package com.kshirsa.userservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.BaseController;
import com.kshirsa.coreservice.SuccessResponse;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.userservice.UserConstants;
import com.kshirsa.userservice.dto.request.OtpValidateRequest;
import com.kshirsa.userservice.dto.response.GenerateOtpResponse;
import com.kshirsa.userservice.dto.response.LoginResponse;
import com.kshirsa.userservice.dto.response.NewTokenResponse;
import com.kshirsa.userservice.service.declaration.UserAuthService;
import com.kshirsa.userservice.service.declaration.UserOtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = BaseConstants.ROOT_PATH + "/auth", produces = "application/json")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Public Controller", description = "Public endpoints for user signup, login, email validation")
public class UserAuthController extends BaseController {

    private final UserOtpService userOtpService;
    private final UserAuthService userAuthService;
    private final Environment env;

    @Operation(summary = "Generate OTP for email validation for sign up & login")
    @GetMapping(path = "/otp")
    public ResponseEntity<SuccessResponse<GenerateOtpResponse>> generateEmailOtp(@Pattern(regexp= UserConstants.EMAIL_REGEX, message = "INVALID_EMAIL_FORMAT")
                                                                                     @NotNull String email) throws CustomException, MessagingException {
        return ok(userOtpService.generateOtp(email), env.getProperty("OTP.SENT") + email);
    }

    @Operation(summary = "Validate OTP for login & get access tokens in response")
    @PostMapping("/otp/validate")
    public ResponseEntity<SuccessResponse<LoginResponse>> otpValidate(@RequestHeader(value = UserConstants.DEVICE_ID)@NonNull String deviceId,
                                                                      @RequestBody @Valid OtpValidateRequest request,
                                                                      HttpServletRequest httpRequest) throws CustomException, MessagingException {
        return ok(userAuthService.otpValidateFlow(request,deviceId, httpRequest), env.getProperty("USER.LOGGEDIN"));
    }

    @Operation(summary = "Get new JWT token from refresh token")
    @GetMapping(path = "/refresh")
    public ResponseEntity<SuccessResponse<NewTokenResponse>> refreshToken(@RequestHeader(value = UserConstants.DEVICE_ID)@NonNull String deviceId,
                                                                          @NonNull String token) throws CustomException {
        return ok(userAuthService.refreshToken(token,deviceId), env.getProperty("NEW.TOKEN.GENERATED"));
    }

    @Operation(summary = "Logout user and delete Refresh Token")
    @PostMapping(path = "/logout")
    public ResponseEntity<SuccessResponse<Boolean>> logout(@NonNull String token) {
        return ok(userAuthService.logout(token), env.getProperty("NEW.TOKEN.GENERATED"));
    }

    @Operation(summary = "Unauthenticated endpoint to check if the service is up and running.")
    @GetMapping("/index")
    public String index() {
        return BaseConstants.INDEX_RESPONSE;
    }
}
