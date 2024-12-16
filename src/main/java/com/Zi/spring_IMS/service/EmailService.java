package com.Zi.spring_IMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final String recipientEmail = "zinkxywork@gmail.com"; // manager email

    @Autowired
    public EmailService (JavaMailSender mailSender) { this.mailSender = mailSender;}

    @Async
    public void sendReorderAlert(String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
