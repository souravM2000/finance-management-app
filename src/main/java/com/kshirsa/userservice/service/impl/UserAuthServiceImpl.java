package com.kshirsa.userservice.service.impl;

import com.kshirsa.coreservice.BaseConstants;
import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.userservice.dto.request.OtpValidateRequest;
import com.kshirsa.userservice.dto.response.LoginResponse;
import com.kshirsa.userservice.dto.response.NewTokenResponse;
import com.kshirsa.userservice.entity.RefreshToken;
import com.kshirsa.userservice.entity.UserDetails;
import com.kshirsa.userservice.repository.UserDetailsRepository;
import com.kshirsa.userservice.security.JwtHelper;
import com.kshirsa.notificationservice.EmailService;
import com.kshirsa.userservice.service.RefreshTokenService;
import com.kshirsa.userservice.service.declaration.UserAuthService;
import com.kshirsa.userservice.service.declaration.UserOtpService;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final JwtHelper jwtHelper;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsRepository userRepository;
    private final UserOtpService userOtpService;
    private final EmailService emailService;

    @Override
    public LoginResponse otpValidateFlow(OtpValidateRequest request) throws CustomException, MessagingException {
        if (userOtpService.validateOtp(request.email(), request.otp())) {
            UserDetails user = userRepository.findByUserEmail(request.email());
            if (user == null) {
                user = userRepository.save(new UserDetails(request.email(), LocalDateTime.now()));

                if (!BaseConstants.ADMIN_EMAILS.contains(request.email()))
                    emailService.sendWelcomeMail(user.getUserEmail());

                RefreshToken token = refreshTokenService.getRefreshToken(user.getUserId());
                return new LoginResponse(jwtHelper.generateToken(user.getUserId()),
                        token.getToken(), token.getExpirationDate(),
                        false,
                        user);
            } else {
                RefreshToken token = refreshTokenService.getRefreshToken(user.getUserId());
                return new LoginResponse(jwtHelper.generateToken(user.getUserId()),
                        token.getToken(), token.getExpirationDate(),
                        isSignupFlowCompleted(user),
                        user);
            }
        } else
            throw new CustomException(ErrorCode.INVALID_OTP.name());
    }

    @Override
    public NewTokenResponse refreshToken(@NonNull String token) throws CustomException {
        Integer userid = refreshTokenService.tokenValidation(token);
        return new NewTokenResponse(jwtHelper.generateToken(userid),
                refreshTokenService.extendTokenTime(token));
    }

    @Override
    public Boolean logout(@NonNull String token) {
        refreshTokenService.deleteToken(token);
        return true;
    }

    public Boolean isSignupFlowCompleted(UserDetails user) {
        return user != null && user.getName() != null
                && user.getPhoneNumber() != null
                && user.getCountryCode() != null
                && user.getGender() != null
                && user.getDob() != null;
    }
}
