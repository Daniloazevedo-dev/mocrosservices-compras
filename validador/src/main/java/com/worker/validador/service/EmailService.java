package com.worker.validador.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.worker.validador.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    @Value("${spring.mail.username}")
    private String username;

    public void notificarClienteCompraComSucesso(String email) {

        try {
            sendEmailCompraComSucesso(email);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public void notificarClienteSaldoInsuficiente(String email) {

        try {
            sendEmailSaldoInsuficiente(email);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public void notificarClienteLimiteIndisponivel(String email) {

        try {
            sendEmailLimiteIndisponivel(email);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @SneakyThrows(IOException.class)
    private void sendEmailCompraComSucesso(String email)  {
        Email from = new Email(username);
        Email to = new Email(email);
        Content content = new Content("text/plain", "Sua compra foi aprovada! Breve você receberá seu codigo de rastreio.");
        String subject = "Compra Confirmada";
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            log.error("Erro ao enviar e-mail: {}", response.getBody());
            throw new RuntimeException("Erro ao enviar de compra aprovada e-mail");
        } else {
            log.info("Cliente notificado da compra aprovada com sucesso!");
        }

    }

    @SneakyThrows(IOException.class)
    private void sendEmailSaldoInsuficiente(String email)  {
        Email from = new Email(username);
        Email to = new Email(email);
        Content content = new Content("text/plain", "Você não tem limite disponível para essa efetuar compra.");
        String subject = "Saldo Indisponível";
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            log.error("Erro ao enviar e-mail: {}", response.getBody());
            throw new RuntimeException("Erro ao enviar de limite indisponivel e-mail");
        } else {
            log.info("Cliente notificado da compra com limite indisponivel!");
        }
    }
    @SneakyThrows(IOException.class)
    private void sendEmailLimiteIndisponivel(String email)  {
        Email from = new Email(username);
        Email to = new Email(email);
        Content content = new Content("text/plain", "Limite indisponível.");
        String subject = "Limite indisponível";
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            log.error("Erro ao enviar e-mail: {}", response.getBody());
            throw new RuntimeException("Erro ao enviar de Limite indisponível");
        } else {
            log.info("Cliente notificado da compra Limite indisponível!");
        }

    }

}
