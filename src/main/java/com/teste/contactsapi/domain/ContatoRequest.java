package com.teste.contactsapi.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoRequest {

    @NotEmpty
    private String nome;

    @NotEmpty
    private String contato;

    @NotNull
    private Long profissionalId;
}
