package com.teste.contactsapi.mappers;

import com.teste.contactsapi.domain.Profissional;
import com.teste.contactsapi.domain.ProfissionalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProfissionalMapper {

    List<ProfissionalResponse> listToResponse(List<Profissional> profissionais);

    ProfissionalResponse toResponse(Profissional profissional);
}
