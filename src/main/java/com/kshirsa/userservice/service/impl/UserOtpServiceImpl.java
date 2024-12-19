package com.kshirsa.userservice.service.impl;

import com.kshirsa.coreservice.ErrorCode;
import com.kshirsa.userservice.exception.UserException;
import com.kshirsa.userservice.exernalservice.CheckMailConstants;
import com.kshirsa.userservice.exernalservice.CheckMailResponse;
import com.kshirsa.userservice.service.declaration.UserOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class UserOtpServiceImpl implements UserOtpService {

    @Value("${checkMail.key}")
    private String checkMailKey;
    @Value("${checkMail.host}")
    private String checkMailHost;

    private final RestClient restClient;

    @Override
    public Boolean validateEmail(String email) throws UserException {
        CheckMailResponse res = restClient
                .get()
                .uri(CheckMailConstants.CHECK_MAIL_URI + email)
                .header(CheckMailConstants.CHECK_MAIL_API_KEY, checkMailKey)
                .header(CheckMailConstants.CHECK_MAIL_HOST, checkMailHost)
                .retrieve().toEntity(CheckMailResponse.class).getBody();
        if(res != null)
        {
            if (res.isValid() && !res.isDisposable())
                return true;
            else if (res.isDisposable())
                throw new UserException(ErrorCode.DISPOSABLE_EMAIL.name());
            else
                throw new UserException(ErrorCode.INVALID_USER_ID.name());
        }
        return false;
    }

    @Override
    public String generateOtp(String email) {
        return "";
    }
}
