package com.example.notification_system.service.impl;

import com.example.notification_system.configuration.SendgridConfig;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailSender {

    @Autowired
    SendgridConfig sendgridconfig;

    public void sendEmail(String toEmail, String subject, String message) throws IOException {
        Email from = new Email(sendgridconfig.getFromEmail(), sendgridconfig.getFromName());
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridconfig.getApiKey());
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
