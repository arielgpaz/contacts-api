package com.teste.contactsapi.controllers;

import com.teste.contactsapi.domain.ContatoRequest;
import com.teste.contactsapi.domain.ContatoResponse;
import com.teste.contactsapi.services.ContatosService;
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
    public List<ContatoResponse> buscarContatos(
            @RequestParam String q,
            @RequestParam(required = false) List<String> fields
    ) {

        return contatosService.buscarContatosPorFiltros(q, fields);
    }

    @GetMapping("/{id}")
    public ContatoResponse buscarContato(@PathVariable Long id) {

        return contatosService.buscarContatoResponsePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContatoResponse cadastrarContato(@RequestBody ContatoRequest contatoRequest) {

        return contatosService.cadastrarContato(contatoRequest);
    }

    @PutMapping("/{id}")
    public void atualizarContato(
            @PathVariable Long id,
            @RequestBody ContatoRequest contatoRequest
    ) {
        contatosService.atualizarContato(id, contatoRequest);
    }

    @DeleteMapping("/{id}")
    public void excluirContato(@PathVariable Long id) {

        contatosService.excluirContato(id);

    }
}
