package com.teste.contactsapi.controllers;

import com.teste.contactsapi.domain.ContatoRequest;
import com.teste.contactsapi.domain.ContatoResponse;
import com.teste.contactsapi.services.ContatosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
@AllArgsConstructor
public class ContatosController {

    private final ContatosService contatosService;

    @GetMapping
    @Operation(
            summary = "Buscar contatos",
            description = "Endpoint para buscas personalizadas")
    public List<ContatoResponse> buscarContatos(
            @Parameter(description = "String que pode estar contida em qualquer campo dos contatos")
            @RequestParam String q,
            @Parameter(description = "Campos que serão retornados")
            @RequestParam(required = false) List<String> fields
    ) {

        return contatosService.buscarContatosPorFiltros(q, fields);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar contatos por id",
            description = "Endpoint para buscar por id")
    public ContatoResponse buscarContato(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador único do contato")
            @PathVariable Long id
    ) {

        return contatosService.buscarContatoResponsePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar contatos",
            description = "Endpoint para cadastrar novos contatos")
    public ContatoResponse cadastrarContato(
            @Parameter(description = "Informações do contato a ser cadastrado")
            @RequestBody ContatoRequest contatoRequest
    ) {

        return contatosService.cadastrarContato(contatoRequest);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar contatos",
            description = "Endpoint para atualizar contatos já cadastrados")
    public void atualizarContato(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador único do contato")
            @PathVariable Long id,
            @Parameter(description = "Novas informações do contato a ser atualizado")
            @RequestBody ContatoRequest contatoRequest
    ) {

        contatosService.atualizarContato(id, contatoRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir contatos",
            description = "Endpoint para excluir contatos que não serão mais usados")
    public void excluirContato(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador único do contato")
            @PathVariable Long id
    ) {

        contatosService.excluirContato(id);

    }
}
