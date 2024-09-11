package com.Smart_contact_manager.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Smart_contact_manager.services.EmailService;

@Service
public class EmailServiceImpl  implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Override
    public void sendEmail(String to, String subject, String body) {
      SimpleMailMessage message=  new SimpleMailMessage();
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);
      message.setFrom("scm@demomailtrap.com");
      emailSender.send(message);
    }

    @Override
    public void sendEmailWithHtml() {
        
    }

    @Override
    public void sendEmailWithAttachment() {
        
    }

}
