package com.mscompra.service.rabbitmq;

import com.mscompra.model.Pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "${queue.name}")
    public void consumer(@Payload Pedido pedido) {
        System.out.println("Payload: " + pedido);
    }

}
