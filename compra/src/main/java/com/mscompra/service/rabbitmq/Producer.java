package com.mscompra.service.rabbitmq;

import com.mscompra.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void enviarPedido(Pedido pedido) {
        rabbitTemplate.convertAndSend(queue.getName(), pedido);
    }

}
