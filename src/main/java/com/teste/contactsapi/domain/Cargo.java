package com.teste.contactsapi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Cargo {

    DESENVOLVEDOR("Desenvolvedor"),
    DESIGNER("Designer"),
    SUPORTE("Suporte"),
    TESTER("Tester");

    private final String nome;

    public static List<Cargo> fromString(String q) {
        return Arrays.stream(Cargo.values())
                .filter(cargo -> cargo.name().contains(q.toUpperCase()))
                .toList();
    }

}
