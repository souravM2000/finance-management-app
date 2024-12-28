package com.kshirsa.notificationservice;

import com.kshirsa.userservice.UserConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.kshirsa.notificationservice.EmailTemplates.welcomeHtml;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("spring.mail.username")
    String fromEmail;

    private final JavaMailSender mailSender;

    public void sendOtpByEmail(EmailValidationType emailType, String email, String otp) throws MessagingException {
        String subject = "Hello from Kshirsa! Your OTP is " + otp;
        sendEmail(welcomeHtmlTemplate(emailType, otp), subject, email.toLowerCase());
    }

    public void sendWelcomeMail(String email) throws MessagingException {
        String subject = "Welcome to Kshirsa!";
        sendEmail(welcomeHtml, subject, email.toLowerCase());
    }

    public void sendNewDeviceLoginEmail(String email, String location) throws MessagingException {
        String subject = "New Device Login";
        sendEmail(newDeviceHtmlTemplate(location), subject, email.toLowerCase());
    }

    @Async
    public void sendEmail(String content, String subject, String toEmail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        mimeMessage.setContent(content, "text/html");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom(fromEmail);
        mailSender.send(mimeMessage);
    }

    public String welcomeHtmlTemplate(EmailValidationType emailType, String otp) {
        return String.format(EmailTemplates.otpHtmlTemplate, emailType.getFirstLine(), otp, UserConstants.OTP_VALIDITY);
    }

    public String newDeviceHtmlTemplate(String location) {
        return String.format(EmailTemplates.newDeviceLogin, location,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' h:mm a '(IST)'")));
    }
}
