package com.teste.contactsapi.controllers;

import com.teste.contactsapi.domain.ProfissionalRequest;
import com.teste.contactsapi.domain.ProfissionalResponse;
import com.teste.contactsapi.services.ProfissionaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
@AllArgsConstructor
public class ProfissionaisController {

    private final ProfissionaisService profissionaisService;

    @GetMapping
    @Operation(
            summary = "Buscar profissionais",
            description = "Endpoint para buscas personalizadas")
    public List<ProfissionalResponse> buscarProfissionais(
            @Parameter(description = "String que pode estar contida em qualquer campo dos profissionais")
            @RequestParam String q,
            @Parameter(description = "Campos que serão retornados")
            @RequestParam(required = false) List<String> fields
    ) {

        return profissionaisService.buscarProfissionaisPorFiltros(q, fields);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar profissionais por id",
            description = "Endpoint para buscar por id")
    public ProfissionalResponse buscarProfissional(
            @Parameter(description = "Identificador único do profissional")
            @PathVariable Long id
    ) {

        return profissionaisService.buscarProfissionalResponsePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar profissionais",
            description = "Endpoint para cadastrar novos profissionais")
    public ProfissionalResponse cadastrarProfissional(
            @Parameter(description = "Informações do profissional a ser cadastrado")
            @RequestBody ProfissionalRequest profissionalRequest
    ) {

        return profissionaisService.cadastrarProfissional(profissionalRequest);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar profissionais",
            description = "Endpoint para atualizar profissionais já cadastrados")
    public void atualizarProfissional(
            @Parameter(description = "Identificador único do profissional")
            @PathVariable Long id,
            @Parameter(description = "Novas informações do profissional a ser atualizado")
            @RequestBody ProfissionalRequest profissionalRequest
    ) {

        profissionaisService.atualizarProfissional(id, profissionalRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir profissionais",
            description = "Endpoint para exclusão lógica de profissionais que não serão mais usados")
    public void excluirProfissional(
            @Parameter(description = "Identificador único do profissional")
            @PathVariable Long id
    ) {

        profissionaisService.excluirProfissional(id);
    }
}
