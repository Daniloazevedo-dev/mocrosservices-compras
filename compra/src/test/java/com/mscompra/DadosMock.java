package com.mscompra;

import com.mscompra.model.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

public class DadosMock {

    public Pedido getPedido() {
        return Pedido.builder()
                .nome("Danilo Azevedo")
                .produto(1L)
                .dataCompra(new Date())
                .cpfCliente("123.456.789-10")
                .cep("46400000")
                .email("danilo.ann@gmail.com")
                .valor(BigDecimal.TEN)
                .build();
    }

    public Pedido getPedidoSalvo() {
        return Pedido.builder()
                .id(1L)
                .nome("Danilo Azevedo")
                .produto(1L)
                .dataCompra(new Date())
                .cpfCliente("123.456.789-10")
                .cep("46400000")
                .email("danilo.ann@gmail.com")
                .valor(BigDecimal.TEN)
                .build();
    }

}
