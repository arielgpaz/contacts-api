package com.teste.contactsapi.controllers;

import com.teste.contactsapi.domain.ProfissionalRequest;
import com.teste.contactsapi.domain.ProfissionalResponse;
import com.teste.contactsapi.services.ProfissionaisService;
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
    public List<ProfissionalResponse> buscarProfissionais(
            @RequestParam String q,
            @RequestParam(required = false) List<String> fields
    ) {
        return profissionaisService.buscarProfissionaisPorFiltros(q, fields);
    }

    @GetMapping("/{id}")
    public ProfissionalResponse buscarProfissional(@PathVariable Long id) {

        return profissionaisService.buscarProfissionalResponsePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfissionalResponse cadastrarProfissional(@RequestBody ProfissionalRequest profissionalRequest) {

        return profissionaisService.cadastrarProfissional(profissionalRequest);
    }

    @PutMapping("/{id}")
    public void atualizarProfissional(
            @PathVariable Long id,
            @RequestBody ProfissionalRequest profissionalRequest
    ) {
        profissionaisService.atualizarProfissional(id, profissionalRequest);
    }

    @DeleteMapping("/{id}")
    public void excluirProfissional(@PathVariable Long id) {

        profissionaisService.excluirProfissional(id);
    }
}
