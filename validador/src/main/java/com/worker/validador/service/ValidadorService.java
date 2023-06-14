package com.worker.validador.service;

import com.worker.validador.model.Cartao;
import com.worker.validador.model.Pedido;
import com.worker.validador.service.exceptions.LimiteIndisponivelException;
import com.worker.validador.service.exceptions.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class ValidadorService {

    private final EmailService emailService;

    public void validar(Pedido pedido) {
        validaLimiteDisponivel(pedido.getCartao(), pedido.getEmail());
        validarCompraComLimite(pedido);
        emailService.notificarClienteCompraComSucesso(pedido.getEmail());
    }

    private void validarCompraComLimite(Pedido pedido) {
        if (pedido.getValor().longValue() > pedido.getCartao().getLimiteDisponivel().longValue()) {
            log.error("Valor do pedido: {}. Limite disponível: {}", pedido.getValor(), pedido.getCartao().getLimiteDisponivel());
            emailService.notificarClienteSaldoInsuficiente(pedido.getEmail());
            throw new SaldoInsuficienteException("Você não tem limite disponível para essa efetuar compra");
        }
    }

    private void validaLimiteDisponivel(Cartao cartao, String email) {
        if(cartao.getLimiteDisponivel().longValue() <= 0) {
            emailService.notificarClienteLimiteIndisponivel(email);
            throw new LimiteIndisponivelException("Limite indisponível");
        }
    }

}
