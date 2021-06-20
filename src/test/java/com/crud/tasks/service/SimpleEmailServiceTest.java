package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;


    private final static Properties mailProperties = new Properties();

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private MailCreatorService mailCreatorService;
    @Captor
    private ArgumentCaptor<MimeMessage> captureMessageWithCC;
    @Captor
    private ArgumentCaptor<MimeMessage> captureMessageWithoutCC;

    public static void comparingHeaders(MimeMessage actualMimeMessage, MimeMessage expectedMimeMessage) throws IOException, MessagingException {
        assertArrayEquals(actualMimeMessage.getHeader("To"), expectedMimeMessage.getHeader("To"));
        assertArrayEquals(actualMimeMessage.getHeader("Cc"), expectedMimeMessage.getHeader("Cc"));
        assertArrayEquals(actualMimeMessage.getHeader("Subject"), expectedMimeMessage.getHeader("Subject"));

    }

    @Test
    public void shouldSendEmailWithCC() throws MessagingException, IOException {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("test")
                .message("test message")
                .toCc("test recipients")
                .build();
        Session session = Session.getDefaultInstance(mailProperties);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());
        when(mailCreatorService.buildTrelloCardEmail(mail.getMessage())).thenReturn("test");
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage(session));

        //When
        simpleEmailService.send(mail, false);

        //Then
        verify(javaMailSender).send(captureMessageWithCC.capture());
        captureMessageWithCC.getValue();
        MimeMessage actualMimeMessage = new MimeMessage(session);
        MimeMessage expectedMimeMessage = new MimeMessage(session);

        comparingHeaders(actualMimeMessage, expectedMimeMessage);
    }

    @Test
    public void shouldSendEmailWithoutCC() throws MessagingException, IOException {
        //Given
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("test")
                .message("test message")
                .toCc(null)
                .build();


        Session session = Session.getDefaultInstance(mailProperties);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        when(mailCreatorService.buildTrelloCardEmail(mail.getMessage())).thenReturn("test");
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage(session));

        //When
        simpleEmailService.send(mail, false);

        //Then
        verify(javaMailSender).send(captureMessageWithoutCC.capture());
        captureMessageWithoutCC.getValue();
        MimeMessage actualMimeMessage = new MimeMessage(session);
        MimeMessage expectedMimeMessage = new MimeMessage(session);

        comparingHeaders(actualMimeMessage, expectedMimeMessage);
    }
}