package com.worker.validador.service.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
}
