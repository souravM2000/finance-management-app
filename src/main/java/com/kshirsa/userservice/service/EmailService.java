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

    @Async
    public void sendOtpByEmail(EmailValidationType emailType, String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        mimeMessage.setContent(htmlTemplate(emailType,otp), "text/html");
        helper.setTo(email.toLowerCase());
        helper.setSubject(otp + " : OTP to verify your Email for Kshirsa");
        helper.setFrom(fromEmail);
        mailSender.send(mimeMessage);
    }

    public String htmlTemplate(EmailValidationType emailType, String otp) {

        return String.format(htmlTemplate,emailType.getFirstLine() ,otp, UserConstants.OTP_VALIDITY);
    }
         String htmlTemplate="""
         <!DOCTYPE html>
         <html lang="en">
         <head>
             <meta http-equiv="Content-Type" content="text/html; charset=us-ascii">
             <style>
                 body {
                     font-family: Arial, sans-serif;
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
                     /* display: table-cell; */
                     vertical-align: middle;
                     text-align: center;
                   font-size: 16px;
                 }
                 .otp-text {
                     background-color: #018665;
                     color: white;
                     padding: 8px 35px;
                     border-radius: 4px;
                     cursor: pointer;
                 }
             </style>
         </head>
         <body>
         <div class="container">
             <p> %s Please use the following</p>
             <p style="font-size: 20px;font-weight: bold;">VERIFICATION CODE</p>
             <b class="otp-text">%s</b>
             <p>This OTP is valid for only <b>%s</b> minutes.</p>
             <p>If you didn't sign up for an account with us, you can safely ignore this email.</p>
             <p>Best Regards,
             <h3 style="color: #018665;">Team Kshirsa</h3>
             </p>
         </div>
         </body>
         </html>
         """;
}
