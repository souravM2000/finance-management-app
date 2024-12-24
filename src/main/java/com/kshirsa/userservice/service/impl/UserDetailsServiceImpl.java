package com.kshirsa.userservice.service.impl;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.coreservice.exception.ErrorCode;
import com.kshirsa.userservice.dto.request.SignUpRequest;
import com.kshirsa.userservice.entity.UserDetails;
import com.kshirsa.userservice.repository.UserDetailsRepository;
import com.kshirsa.userservice.service.declaration.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userRepository;

    @Override
    public Integer getUser() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return Integer.parseInt(authentication.getName());
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND.name());
        }
    }

    @Override
    public UserDetails updateUserDetails(SignUpRequest request) throws CustomException {
        UserDetails user = userRepository.findById(getUser()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.name()));
        user.setName(request.name());
        user.setPhoneNumber(request.phoneNumber());
        user.setCountryCode(request.countryCode());
        user.setCountry(request.country());
        user.setGender(request.gender());
        user.setDob(request.dob());
        return userRepository.save(user);
    }

    @Override
    public UserDetails getUserDetails() throws CustomException {
        return userRepository.findById(getUser()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.name()));
    }
}
