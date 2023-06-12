package com.workercompras.repository;

import com.workercompras.model.Endereco;
import lombok.SneakyThrows;

public class HystrixClientFallback implements CepRepository {

    @Override
    @SneakyThrows
    public Endereco buscarPorCep(String cep) {
        throw new Exception("Cep não encontrado.");
    }
}
