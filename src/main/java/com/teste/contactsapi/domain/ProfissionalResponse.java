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
public class ProfissionalResponse {

    private Long id;

    private String nome;

    private Cargo cargo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nascimento;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
}
