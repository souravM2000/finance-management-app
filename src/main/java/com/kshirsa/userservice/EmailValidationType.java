package com.kshirsa.userservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailValidationType {
    SIGN_UP("Thank you for signing up with <b>Kshirsa</b>! Please enter the code to activate your account."),
    LOGIN("You are attempting to log in to your <b>Kshirsa</b> account. If this wasn't you, please contact support."),
    CHANGE_EMAIL("You recently requested to change your email for your <b>Kshirsa</b> account. Enter the code to confirm this change.");

    private final String firstLine;
}
