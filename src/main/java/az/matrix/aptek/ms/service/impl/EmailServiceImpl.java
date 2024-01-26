package az.matrix.aptek.ms.service.impl;

import az.matrix.aptek.ms.service.EmailService;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService
{

    @Value("${spring.mail.username}")
    private String mail;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String[] to, String subject, String text)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try
        {
            Address[] addresses = new Address[to.length];
            for (int i = 0; i < to.length; i++)
                addresses[i] = new InternetAddress(to[i]);
            mimeMessage.setFrom(new InternetAddress(mail));
            mimeMessage.setRecipients(MimeMessage.RecipientType.CC, addresses);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(text, "text/html; charset=utf-8");

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e)
        {
            log.error(e.getMessage());
        }
    }
}
