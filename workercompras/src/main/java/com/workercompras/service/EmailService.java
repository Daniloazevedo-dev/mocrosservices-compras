package com.workercompras.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.workercompras.model.Pedido;
import com.workercompras.service.producer.PedidoProducer;
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

    private final PedidoProducer pedidoProducer;

    public void notificarCliente(Pedido pedido) {

        try {
            sendEmail(pedido.getEmail());
            log.info("Preparando pedido consumer...");
            pedidoProducer.enviarPedido(pedido);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @SneakyThrows(IOException.class)
    private void sendEmail(String email)  {
        Email from = new Email(username);
        Email to = new Email(email);
        Content content = new Content("text/plain", "Este é um e-mail de confirmação de compra recebida. " +
                "Agora vamos aprovar sua compra e brevemente você receberá um novo e-mail de confirmação." +
                "\nObrigado por comprar com a gente!!");
        String subject = "Compra recebida";
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        if (response.getStatusCode() < 200 || response.getStatusCode() > 299) {
            log.error("Erro ao enviar e-mail: {}", response.getBody());
            throw new RuntimeException("Erro ao enviar e-mail");
        } else {
            log.info("E-mail enviado com sucesso para {}", email);
        }

    }
}
