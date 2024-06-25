package com.java_ne.services.interfaces;

import com.java_ne.dtos.mail.EmailDetails;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);

    void sendTemplatedHtmlEmail(String to, String subject, String templateName, Context context) throws MessagingException;
}