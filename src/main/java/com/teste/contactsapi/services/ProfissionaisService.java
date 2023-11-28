package com.teste.contactsapi.services;

import com.teste.contactsapi.domain.Cargo;
import com.teste.contactsapi.domain.Profissional;
import com.teste.contactsapi.domain.ProfissionalRequest;
import com.teste.contactsapi.domain.ProfissionalResponse;
import com.teste.contactsapi.exceptions.CampoSolicitadoNaoExisteException;
import com.teste.contactsapi.exceptions.ProfissionalNaoEncontradoException;
import com.teste.contactsapi.mappers.ProfissionalMapper;
import com.teste.contactsapi.repositories.ProfissionaisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class ProfissionaisService {

    private final ProfissionaisRepository profissionaisRepository;
    private final ProfissionalMapper profissionalMapper;

    public List<ProfissionalResponse> buscarProfissionaisPorFiltros(String q, List<String> fields) {

        List<Profissional> profissionais = profissionaisRepository.findByRequestedString(q, Cargo.fromString(q));

        if (nonNull(fields) && !fields.isEmpty()) {
            return filtrarCampos(profissionais, fields);
        }

        return profissionalMapper.listToResponse(profissionais);
    }

    private List<ProfissionalResponse> filtrarCampos(List<Profissional> profissionais, List<String> fields) {
        List<ProfissionalResponse> profissionaisResponse = new ArrayList<>();

        profissionais.forEach(profissional ->  {

            ProfissionalResponse profissionalResponse = new ProfissionalResponse();

            fields.forEach(field -> {
                switch (field) {
                    case "id" -> profissionalResponse.setId(profissional.getId());
                    case "nome" -> profissionalResponse.setNome(profissional.getNome());
                    case "cargo" -> profissionalResponse.setCargo(profissional.getCargo());
                    case "nascimento" -> profissionalResponse.setNascimento(profissional.getNascimento());
                    case "createdDate" -> profissionalResponse.setCreatedDate(profissional.getCreatedDate());
                    default -> throw new CampoSolicitadoNaoExisteException(format("O campo %s não existe.", field));
                }
            });

            profissionaisResponse.add(profissionalResponse);
        });

        return profissionaisResponse;
    }

    public ProfissionalResponse buscarProfissionalResponsePorId(Long id) {
        return profissionalMapper.toResponse(buscarProfissionalPorId(id));
    }

    public Profissional buscarProfissionalPorId(Long id) {
        return profissionaisRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException(
                        format("Profissional com id %d não foi encontrado", id)
                ));
    }

    public ProfissionalResponse cadastrarProfissional(ProfissionalRequest profissionalRequest) {

        Profissional profissional = Profissional.builder()
                .nome(profissionalRequest.nome())
                .cargo(profissionalRequest.cargo())
                .nascimento(profissionalRequest.nascimento())
                .build();

        Profissional profissionalSalvo = profissionaisRepository.save(profissional);

        return profissionalMapper.toResponse(profissionalSalvo);
    }

    public void atualizarProfissional(Long id, ProfissionalRequest profissionalRequest) {

        Profissional profissional = buscarProfissionalPorId(id);

        profissional.setNome(profissionalRequest.nome());
        profissional.setCargo(profissionalRequest.cargo());
        profissional.setNascimento(profissionalRequest.nascimento());

        profissionaisRepository.save(profissional);
    }

    public void excluirProfissional(Long id) {

        Profissional profissionalParaExcluir = profissionaisRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ProfissionalNaoEncontradoException(
                        format("Profissional com id %d não foi encontrado", id)
                ));

        profissionalParaExcluir.setDeleted(true);

        profissionaisRepository.save(profissionalParaExcluir);
    }
}
