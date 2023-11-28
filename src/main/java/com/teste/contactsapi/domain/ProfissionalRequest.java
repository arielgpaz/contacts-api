package com.teste.contactsapi.domain;

import java.util.Date;

public record ProfissionalRequest(String nome, Cargo cargo, Date nascimento) {}
