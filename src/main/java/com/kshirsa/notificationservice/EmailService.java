package com.kshirsa.notificationservice;

import com.kshirsa.userservice.UserConstants;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.kshirsa.notificationservice.EmailTemplates.welcomeHtml;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SendAsyncEmail sendAsyncEmail;

    public void sendOtpByEmail(EmailValidationType emailType, String email, String otp) throws MessagingException {
        String subject = "Hello from Kshirsa! Your OTP is " + otp;
        sendAsyncEmail.sendEmail(welcomeHtmlTemplate(emailType, otp), subject, email.toLowerCase());
    }

    public void sendWelcomeMail(String email) throws MessagingException {
        String subject = "Welcome to Kshirsa!";
        sendAsyncEmail.sendEmail(welcomeHtml, subject, email.toLowerCase());
    }

    public void sendNewDeviceLoginEmail(String email, String location) throws MessagingException {
        String subject = "New Device Login";
        sendAsyncEmail.sendEmail(newDeviceHtmlTemplate(location), subject, email.toLowerCase());
    }

    public String welcomeHtmlTemplate(EmailValidationType emailType, String otp) {
        return String.format(EmailTemplates.otpHtmlTemplate, emailType.getFirstLine(), otp, UserConstants.OTP_VALIDITY);
    }

    public String newDeviceHtmlTemplate(String location) {
        return String.format(EmailTemplates.newDeviceLogin, location,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' h:mm a '(IST)'")));
    }
}
