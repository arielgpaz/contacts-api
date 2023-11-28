package com.teste.contactsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfissionalNaoEncontradoException extends RuntimeException {

    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
