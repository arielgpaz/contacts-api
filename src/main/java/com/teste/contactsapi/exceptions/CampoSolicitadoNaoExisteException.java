package com.teste.contactsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampoSolicitadoNaoExisteException extends RuntimeException {

    public CampoSolicitadoNaoExisteException(String mensagem) {
        super(mensagem);
    }
}
