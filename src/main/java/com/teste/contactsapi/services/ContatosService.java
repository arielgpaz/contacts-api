package com.teste.contactsapi.services;

import com.teste.contactsapi.domain.Contato;
import com.teste.contactsapi.domain.ContatoRequest;
import com.teste.contactsapi.domain.ContatoResponse;
import com.teste.contactsapi.domain.Profissional;
import com.teste.contactsapi.exceptions.CampoSolicitadoNaoExisteException;
import com.teste.contactsapi.exceptions.ContatoNaoEncontradoException;
import com.teste.contactsapi.mappers.ContatoMapper;
import com.teste.contactsapi.mappers.ProfissionalMapper;
import com.teste.contactsapi.repositories.ContatosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class ContatosService {

    private final ContatosRepository contatosRepository;
    private final ProfissionaisService profissionaisService;
    private final ContatoMapper contatoMapper;
    private final ProfissionalMapper profissionalMapper;

    public List<ContatoResponse> buscarContatosPorFiltros(String q, List<String> fields) {

        List<Contato> contatos = contatosRepository.findByRequestedString(q, q);

        if (nonNull(fields) && !fields.isEmpty()) {
            return filtrarCampos(contatos, fields);
        }

        return contatoMapper.listToResponse(contatos);
    }

    private List<ContatoResponse> filtrarCampos(List<Contato> contatos, List<String> fields) {
        List<ContatoResponse> contatosResponse = new ArrayList<>();

        contatos.forEach(contato ->  {

            ContatoResponse contatoResponse = new ContatoResponse();

            fields.forEach(field -> {
                switch (field) {
                    case "id" -> contatoResponse.setId(contato.getId());
                    case "nome" -> contatoResponse.setNome(contato.getNome());
                    case "contato" -> contatoResponse.setContato(contato.getContato());
                    case "createdDate" -> contatoResponse.setCreatedDate(contato.getCreatedDate());
                    case "profissional" -> contatoResponse.setProfissional(profissionalMapper.toResponse(contato.getProfissional()));
                    default -> throw new CampoSolicitadoNaoExisteException(format("O campo %s não existe.", field));
                }
            });

            contatosResponse.add(contatoResponse);

        });

        return contatosResponse;
    }

    public ContatoResponse buscarContatoResponsePorId(Long id) {
        return contatoMapper.toResponse(buscarContatoPorId(id));
    }

    public Contato buscarContatoPorId(Long id) {
        return contatosRepository.findById(id)
                .orElseThrow(() -> new ContatoNaoEncontradoException(
                        format("Contato com id %d não encontrado.", id)
                ));
    }

    public ContatoResponse cadastrarContato(ContatoRequest contatoRequest) {

        Profissional profissional = profissionaisService.buscarProfissionalPorId(contatoRequest.profissionalId());

        Contato contato = Contato.builder()
                .nome(contatoRequest.nome())
                .contato(contatoRequest.contato())
                .profissional(profissional)
                .build();

        Contato contatoSalvo = contatosRepository.save(contato);

        return contatoMapper.toResponse(contatoSalvo);
    }

    public void atualizarContato(Long id, ContatoRequest contatoRequest) {

        Contato contato = buscarContatoPorId(id);
        Profissional profissional = profissionaisService.buscarProfissionalPorId(contatoRequest.profissionalId());

        contato.setNome(contatoRequest.nome());
        contato.setContato(contatoRequest.contato());
        contato.setProfissional(profissional);

        contatosRepository.save(contato);
    }

    public void excluirContato(Long id) {

        contatosRepository.deleteById(id);
    }
}
