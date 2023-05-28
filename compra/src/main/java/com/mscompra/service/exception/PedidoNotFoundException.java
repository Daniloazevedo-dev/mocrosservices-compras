package com.mscompra.service.exception;

public class PedidoNotFoundException extends EntidadeNaoEncontradaException {

    public PedidoNotFoundException(String mensagem) {
        super(mensagem);
    }

    public PedidoNotFoundException(Long id) {
        this(String.format("Não existe um pedido com o id: %s na base de dados!", id));
    }

}
