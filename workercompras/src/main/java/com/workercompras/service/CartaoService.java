package com.workercompras.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {

    public String gerarCartao() {
        var seq1 = new BigDecimal(1000 + (long)(Math.random() * 500));
        var seq2 = new BigDecimal(2000 + (long)(Math.random() * 500));
        var seq3 = new BigDecimal(3000 + (long)(Math.random() * 500));
        var seq4 = new BigDecimal(4000 + (long)(Math.random() * 500));

        return seq1 + " " + seq2 + " " + seq3 + " " + seq4;
    }

    public BigDecimal gerarLimite() {
        return new BigDecimal(1000 + (long)(Math.random() * 500));
    }

}
