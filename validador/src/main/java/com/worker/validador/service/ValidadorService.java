package com.worker.validador.service;

import com.worker.validador.model.Cartao;
import com.worker.validador.model.Pedido;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidadorService {

    public void validar(Pedido pedido) {
        validaLimiteDisponivel(pedido.getCartao());
        validarCompraComLimite(pedido);
    }

    private void validarCompraComLimite(Pedido pedido) {
        if (pedido.getValor().longValue() > pedido.getCartao().getLimiteDisponivel().longValue()) {
            log.error("Valor do pedido: {}. Limite disponível: {}", pedido.getValor(), pedido.getCartao().getLimiteDisponivel());
            throw new RuntimeException("Você não tem limite disponível para essa efetuar compra");
        }
    }

    @SneakyThrows(Exception.class)
    private void validaLimiteDisponivel(Cartao cartao) {
        if(cartao.getLimiteDisponivel().longValue() <= 0) {
            throw new Exception("Limite indisponível");
        }
    }

}
