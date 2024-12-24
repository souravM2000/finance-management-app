package com.kshirsa.userservice.service;

import com.kshirsa.userservice.EmailValidationType;
import com.kshirsa.userservice.UserConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("spring.mail.username")
    String fromEmail;

    private final JavaMailSender mailSender;

    public void sendOtpByEmail(EmailValidationType emailType, String email, String otp) throws MessagingException {
        String subject = "Hello from Kshirsa! Your OTP is " + otp;
        sendEmail(htmlTemplate(emailType, otp), subject, email.toLowerCase());
    }

    public void sendWelcomeMail(String email) throws MessagingException {
        String subject = "Welcome to Kshirsa!";
        sendEmail(welcomeHtml, subject, email.toLowerCase());
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

    public String htmlTemplate(EmailValidationType emailType, String otp) {
        return String.format(otpHtmlTemplate, emailType.getFirstLine(), otp, UserConstants.OTP_VALIDITY);
    }

    String otpHtmlTemplate = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=us-ascii">
                        <style>
                            body {
                                font-family: Arial, Helvetica, sans-serif;
                                line-height: 1.6;
                                background-color: #f4f4f4;
                                margin: 0;
                                padding: 20px;
                            }
                            .container {
                                max-width: 600px;
                                margin: auto;
                                background: #fff;
                                padding: 20px;
                                border-radius: 5px;
                                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                                text-align: center;
                                font-size: 16px;
                            }
                            .otp-text {
                                background-color: #018665;
                                color: white;
                                padding: 10px 40px;
                                border-radius: 4px;
                                display: inline-block;
                                margin: 20px 0;
                                font-size: 20px;
                                font-weight: bold;
                            }
                            .footer {
                                font-size: 14px;
                                color: #777;
                                margin-top: 20px;
                            }
                        </style>
                    </head>
                    <body>
                    <div class="container">
                        <h2 style="color: #018665;">Welcome to Kshirsa!</h2>
                        <p>%s</p>
                        <p style="font-size: 18px; font-weight: bold;">VERIFICATION CODE</p>
                        <div class="otp-text">%s</div>
                        <p>This OTP is valid for only <b>%s</b> minutes.</p>
                        <p>If you didn't sign up for an account with us, you can safely ignore this email.</p>
                        <p>Warm Regards,<br><span style="color: #018665;"><b>Kshirsa Team</b></span></p>
                        <div class="footer">© 2024 Kshirsa. All rights reserved.</div>
                    </div>
                    </body>
                    </html>
            
            """;

    String welcomeHtml = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <style>
                    body {
                        font-family: Arial, Helvetica, sans-serif;
                        line-height: 1.6;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 20px;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: #fff;
                        padding: 20px;
                        border-radius: 5px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        text-align: center;
                    }
                    .header {
                        color: #018665;
                        font-size: 24px;
                        font-weight: bold;
                    }
                    .message {
                        font-size: 16px;
                        color: #333;
                        margin: 20px 0;
                    }
                    .cta-button {
                        display: inline-block;
                        background-color: #018665;
                        color: white;
                        padding: 12px 25px;
                        border-radius: 4px;
                        text-decoration: none;
                        font-weight: bold;
                        font-size: 16px;
                        margin-top: 20px;
                    }
                    .footer {
                        font-size: 14px;
                        color: #777;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
            <div class="container">
                <h2 class="header">Welcome to Kshirsa! 🎉</h2>
                <p class="message">Hi there,</p>
                <p class="message">We’re thrilled to have you on board! Kshirsa is your one-stop personal finance app, designed to help you manage your money effortlessly—from budgeting to tracking expenses, and so much more.</p>
                <p class="message"><b>Quick Tip to Get Started:</b><br>Complete your profile in the app to unlock personalized features and make the most of your experience!</p>
                <p class="message">Take the first step towards mastering your money today!</p>
                <p class="footer">Warm Regards,<br><span style="color: #018665;"><b>Team Kshirsa</b></span></p>
                <div class="footer">© 2024 Kshirsa. All rights reserved.</div>
            </div>
            </body>
            </html>
            
            """;
}
