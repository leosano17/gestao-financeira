package com.fintech.gestao_financeira.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }
}