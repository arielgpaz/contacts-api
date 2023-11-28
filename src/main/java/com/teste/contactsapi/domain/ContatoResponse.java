package com.teste.contactsapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ContatoResponse {

    private Long id;

    private String nome;

    private String contato;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private ProfissionalResponse profissional;

}
