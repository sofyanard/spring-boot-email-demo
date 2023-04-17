package com.sofyanard.springbootemaildemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail,
                          String subject,
                          String body) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            // SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            helper.setFrom("xxxxx@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body, true);
            helper.setSubject(subject);

            javaMailSender.send(mimeMessage);
            System.out.println("email sent");
        }
        catch (MessagingException ex) {
            // Logger.getLogger(HTMLMail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
