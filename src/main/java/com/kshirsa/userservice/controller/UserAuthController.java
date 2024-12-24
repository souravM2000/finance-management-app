package com.kshirsa.userservice.controller;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.BaseController;
import com.kshirsa.coreservice.SuccessResponse;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.userservice.dto.request.OtpValidateRequest;
import com.kshirsa.userservice.dto.response.GenerateOtpResponse;
import com.kshirsa.userservice.dto.response.LoginResponse;
import com.kshirsa.userservice.dto.response.NewTokenResponse;
import com.kshirsa.userservice.service.declaration.UserAuthService;
import com.kshirsa.userservice.service.declaration.UserOtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
    @GetMapping(path="/otp")
    public ResponseEntity<SuccessResponse<GenerateOtpResponse>> generateEmailOtp(@NonNull String email) throws CustomException, MessagingException {
        return ok(userOtpService.generateOtp(email), env.getProperty("OTP.SENT") + email);
    }

    @Operation(summary = "Validate OTP for login & get access tokens in response")
    @PostMapping("/otp/validate")
    public ResponseEntity<SuccessResponse<LoginResponse>> otpValidate(@RequestBody OtpValidateRequest request) throws CustomException {
        return ok(userAuthService.otpValidateFlow(request), env.getProperty("USER.LOGGEDIN"));
    }

    @Operation(summary = "Get new JWT token from refresh token")
    @GetMapping(path="/refresh")
    public ResponseEntity<SuccessResponse<NewTokenResponse>> refreshToken(@NonNull String token) throws CustomException {
        return ok(userAuthService.refreshToken(token), env.getProperty("NEW.TOKEN.GENERATED"));
    }

    @Operation(summary = "Logout user and delete Refresh Token")
    @PostMapping(path="/logout")
    public ResponseEntity<SuccessResponse<Boolean>> logout(@NonNull String token) throws CustomException {
        return ok(userAuthService.logout(token), env.getProperty("NEW.TOKEN.GENERATED"));
    }

    @Operation(summary = "Unauthenticated endpoint to check if the service is up and running.")
    @GetMapping("/index")
    public String index() {
        return BaseConstants.INDEX_RESPONSE;
    }
}
