package com.workercompras.service;

import com.workercompras.model.Endereco;
import com.workercompras.repository.CepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class CepService {

    private final CepRepository cepRepository;

    public Endereco buscarCep(String cep) {
        Endereco endereco =  cepRepository.buscarPorCep(cep);
        log.info("Endereço: {}", endereco);
        return endereco;
    }


}
