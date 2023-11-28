package com.teste.contactsapi.services;

import com.teste.contactsapi.domain.Contato;
import com.teste.contactsapi.domain.ContatoRequest;
import com.teste.contactsapi.domain.ContatoResponse;
import com.teste.contactsapi.domain.Profissional;
import com.teste.contactsapi.exceptions.ContatoNaoEncontradoException;
import com.teste.contactsapi.mappers.ContatoMapper;
import com.teste.contactsapi.repositories.ContatosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatosServiceTest {

    @InjectMocks
    private ContatosService contatosService;

    @Mock
    private ContatosRepository contatosRepository;

    @Mock
    private ProfissionaisService profissionaisService;

    @Mock
    private ContatoMapper contatoMapper;

    @Test
    void test_return_all_contacts_when_no_filters_applied() {

        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1L, "John Doe", "john.doe@example.com", new Date(), null));
        contatos.add(new Contato(2L, "Jane Smith", "jane.smith@example.com", new Date(), null));

        when(contatosRepository.findByRequestedString(anyString(), anyString())).thenReturn(contatos);
        when(contatoMapper.listToResponse(anyList())).thenReturn(Arrays.asList(
                new ContatoResponse(1L, "John Doe", "john.doe@example.com", new Date(), null),
                new ContatoResponse(2L, "Jane Smith", "jane.smith@example.com", new Date(), null)
        ));

        List<ContatoResponse> result = contatosService.buscarContatosPorFiltros("", null);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getNome());
        assertEquals("john.doe@example.com", result.get(0).getContato());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane Smith", result.get(1).getNome());
        assertEquals("jane.smith@example.com", result.get(1).getContato());
    }

    @Test
    void test_return_filtered_contacts_when_filters_applied() {
        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1L, "John Doe", "john.doe@example.com", new Date(), null));

        when(contatosRepository.findByRequestedString(anyString(), anyString())).thenReturn(contatos);
        when(contatoMapper.listToResponse(anyList())).thenReturn(List.of(
                new ContatoResponse(1L, "John Doe", "john.doe@example.com", new Date(), null)
        ));

        List<ContatoResponse> result = contatosService.buscarContatosPorFiltros("John", null);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getNome());
        assertEquals("john.doe@example.com", result.get(0).getContato());
    }

    @Test
    void test_return_empty_list_when_no_contacts_match_filters() {

        List<Contato> contatos = new ArrayList<>();

        when(contatosRepository.findByRequestedString(anyString(), anyString())).thenReturn(contatos);

        List<ContatoResponse> result = contatosService.buscarContatosPorFiltros("John", null);

        assertTrue(result.isEmpty());
    }

    @Test
    void test_handle_empty_fields_parameter_and_return_all_contacts() {

        List<Contato> contatos = new ArrayList<>();
        contatos.add(new Contato(1L, "John Doe", "john.doe@example.com", new Date(), null));
        contatos.add(new Contato(2L, "Jane Smith", "jane.smith@example.com", new Date(), null));

        when(contatosRepository.findByRequestedString(anyString(), anyString())).thenReturn(contatos);
        when(contatoMapper.listToResponse(anyList())).thenReturn(Arrays.asList(
                new ContatoResponse(1L, "John Doe", "john.doe@example.com", new Date(), null),
                new ContatoResponse(2L, "Jane Smith", "jane.smith@example.com", new Date(), null)
        ));

        List<ContatoResponse> result = contatosService.buscarContatosPorFiltros("", Collections.emptyList());

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getNome());
        assertEquals("john.doe@example.com", result.get(0).getContato());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane Smith", result.get(1).getNome());
        assertEquals("jane.smith@example.com", result.get(1).getContato());
    }

    @Test
    void test_valid_id_exists() {
        Long id = 1L;
        Contato expectedContato = new Contato();
        when(contatosRepository.findById(id)).thenReturn(Optional.of(expectedContato));

        Contato actualContato = contatosService.buscarContatoPorId(id);

        assertEquals(expectedContato, actualContato);
    }

    @Test
    void test_invalid_id_not_exists() {
        Long id = 1L;
        when(contatosRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ContatoNaoEncontradoException.class, () -> contatosService.buscarContatoPorId(id));
    }

    @Test
    void test_createContatoAndReturnResponse() {
        ContatoRequest contatoRequest = new ContatoRequest("John Doe", "john.doe@example.com", 1L);
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        when(profissionaisService.buscarProfissionalPorId(1L)).thenReturn(profissional);
        Contato contato = new Contato();
        contato.setId(1L);
        when(contatosRepository.save(any(Contato.class))).thenReturn(contato);
        ContatoResponse expectedResponse = new ContatoResponse();
        expectedResponse.setId(1L);
        when(contatoMapper.toResponse(contato)).thenReturn(expectedResponse);

        ContatoResponse response = contatosService.cadastrarContato(contatoRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void should_update_name_contact_and_professional() {
        Long id = 1L;
        ContatoRequest contatoRequest = new ContatoRequest("New Name", "New Contact", 2L);
        Contato contato = new Contato();
        contato.setId(id);
        Profissional profissional = new Profissional();
        profissional.setId(contatoRequest.profissionalId());
        when(contatosRepository.findById(id)).thenReturn(Optional.of(contato));
        when(profissionaisService.buscarProfissionalPorId(contatoRequest.profissionalId())).thenReturn(profissional);

        contatosService.atualizarContato(id, contatoRequest);

        verify(contatosRepository).save(contato);
        assertEquals(contatoRequest.nome(), contato.getNome());
        assertEquals(contatoRequest.contato(), contato.getContato());
        assertEquals(profissional, contato.getProfissional());
    }

    @Test
    void test_deleteContactById() {
        Long id = 1L;

        contatosService.excluirContato(id);

        verify(contatosRepository, times(1)).deleteById(id);
    }
}
