package com.teste.contactsapi.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProfissionalRequest {

    @NotEmpty
    private String nome;

    @NotEmpty
    private Cargo cargo;

    @NotNull
    private Date nascimento;

}
