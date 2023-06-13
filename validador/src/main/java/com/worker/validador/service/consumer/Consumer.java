package com.worker.validador.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.validador.model.Pedido;
import com.worker.validador.service.ValidadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class Consumer {

    private final ObjectMapper mapper;
    private final ValidadorService validadorService;

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) throws IOException {
        var pedido = mapper.readValue(message.getBody(), Pedido.class);
        log.info("Pedido recebido no Validados: {}", pedido);

        try {
            validadorService.validar(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
