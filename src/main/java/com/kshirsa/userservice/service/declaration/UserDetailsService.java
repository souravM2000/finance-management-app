package com.kshirsa.userservice.service.declaration;

import com.kshirsa.coreservice.exception.CustomException;
import com.kshirsa.userservice.dto.request.SignUpRequest;
import com.kshirsa.userservice.entity.UserDetails;

public interface UserDetailsService {
    Integer getUser() throws CustomException;

    UserDetails updateUserDetails(SignUpRequest request) throws CustomException;

    UserDetails getUserDetails() throws CustomException;
}
