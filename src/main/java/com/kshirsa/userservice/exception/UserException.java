package com.kshirsa.userservice.exception;

import com.kshirsa.coreservice.CustomException;

public class UserException extends CustomException {
        public UserException(String message) {
            super(message);
        }
}
