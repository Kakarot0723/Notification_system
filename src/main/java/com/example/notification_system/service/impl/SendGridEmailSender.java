package com.example.notification_system.service.impl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailSender {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${spring.sendgrid.from-email}")
    private String fromEmail;

    @Value("${spring.sendgrid.from-name}")
    private String fromName;

    public void sendEmail(String toEmail, String subject, String message) throws IOException {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            int statusCode = response.getStatusCode();
            if (statusCode != 200 && statusCode != 202) {
                throw new IOException("Failed to send email: " + response.getBody());
            }
        } catch (IOException ex) {
            // Handle the exception as needed in your application
            throw ex;
        }
    }
}
