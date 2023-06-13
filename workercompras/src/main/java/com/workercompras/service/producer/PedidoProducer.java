package com.workercompras.service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Cartao;
import com.workercompras.model.Pedido;
import com.workercompras.service.CartaoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;
    private final CartaoService cartaoService;

    @SneakyThrows
    public void enviarPedido(Pedido pedido) {

        pedido.setCartao(Cartao.builder()
                        .numero(cartaoService.gerarCartao())
                        .limiteDisponivel(cartaoService.gerarLimite())
                .build());
        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(pedido));
        log.info("Pedido montado com sucesso em worker de compras - PedidoProducer: {}", mapper.writeValueAsString(pedido));
    }

}
