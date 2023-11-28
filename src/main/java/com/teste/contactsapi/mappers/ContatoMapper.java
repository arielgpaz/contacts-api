package com.teste.contactsapi.mappers;

import com.teste.contactsapi.domain.Contato;
import com.teste.contactsapi.domain.ContatoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ContatoMapper {

    ContatoResponse toResponse(Contato contato);

    List<ContatoResponse> listToResponse(List<Contato> contato);
}
