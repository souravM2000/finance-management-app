package com.kshirsa.userservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailValidationType {
    SIGN_UP("Thank you for signing up with <b>Kshirsa</b>!"),
    LOGIN("You are attempting to login to your <b>Kshirsa</b> account."),
    FORGOT_PASSWORD("You recently requested to reset your password for your <b>Kshirsa</b> account.");

    private final String firstLine;
}
