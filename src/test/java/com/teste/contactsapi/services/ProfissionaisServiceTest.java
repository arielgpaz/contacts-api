package com.teste.contactsapi.services;

import com.teste.contactsapi.domain.Cargo;
import com.teste.contactsapi.domain.Profissional;
import com.teste.contactsapi.domain.ProfissionalRequest;
import com.teste.contactsapi.domain.ProfissionalResponse;
import com.teste.contactsapi.exceptions.ProfissionalNaoEncontradoException;
import com.teste.contactsapi.mappers.ProfissionalMapper;
import com.teste.contactsapi.repositories.ProfissionaisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfissionaisServiceTest {

    @InjectMocks
    private ProfissionaisService profissionaisService;

    @Mock
    private ProfissionaisRepository profissionaisRepository;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @Test
    void test_return_all_professionals_when_no_filters_applied() {
        List<Profissional> profissionais = new ArrayList<>();
        profissionais.add(new Profissional(1L, "John Doe", Cargo.DESENVOLVEDOR, new Date(), new Date(), false));
        profissionais.add(new Profissional(2L, "Jane Smith", Cargo.DESIGNER, new Date(), new Date(), false));

        when(profissionaisRepository.findByRequestedString(anyString(), anyList())).thenReturn(profissionais);
        when(profissionalMapper.listToResponse(anyList())).thenReturn(Arrays.asList(
                new ProfissionalResponse(1L, "John Doe", Cargo.DESENVOLVEDOR, new Date(), new Date()),
                new ProfissionalResponse(2L, "Jane Smith", Cargo.DESIGNER, new Date(), new Date())
        ));

        List<ProfissionalResponse> result = profissionaisService.buscarProfissionaisPorFiltros("", null);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getNome());
        assertEquals(Cargo.DESENVOLVEDOR, result.get(0).getCargo());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane Smith", result.get(1).getNome());
        assertEquals(Cargo.DESIGNER, result.get(1).getCargo());
    }

    @Test
    void test_return_filtered_list_of_professionals_when_filters_applied() {
        List<Profissional> profissionais = new ArrayList<>();
        profissionais.add(new Profissional(1L, "John Doe", Cargo.DESENVOLVEDOR, new Date(), new Date(), false));
        profissionais.add(new Profissional(2L, "Jane Smith", Cargo.DESIGNER, new Date(), new Date(), false));

        when(profissionaisRepository.findByRequestedString(anyString(), anyList()))
                .thenReturn(profissionais);
        when(profissionalMapper.listToResponse(anyList())).thenReturn(List.of(
                new ProfissionalResponse(1L, "John Doe", Cargo.DESENVOLVEDOR, new Date(), new Date())
        ));

        List<ProfissionalResponse> result = profissionaisService.buscarProfissionaisPorFiltros("John", null);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John Doe", result.get(0).getNome());
        assertEquals(Cargo.DESENVOLVEDOR, result.get(0).getCargo());
    }

    @Test
    void test_shouldReturnProfessionalWhenIdExistsAndDeletedIsFalse() {
        Long id = 1L;
        Profissional profissional = new Profissional();
        profissional.setId(id);
        when(profissionaisRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(profissional));

        Profissional result = profissionaisService.buscarProfissionalPorId(id);

        assertEquals(profissional, result);
    }

    @Test
    void test_shouldThrowExceptionWhenIdDoesNotExistOrDeletedIsTrue() {
        Long id = 1L;
        when(profissionaisRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThrows(ProfissionalNaoEncontradoException.class, () -> profissionaisService.buscarProfissionalPorId(id));
    }

    @Test
    void test_create_profissional() {
        ProfissionalRequest profissionalRequest = new ProfissionalRequest("John Doe", Cargo.DESENVOLVEDOR, new Date());
        Profissional profissionalSalvo = Profissional.builder()
                .id(1L)
                .nome(profissionalRequest.nome())
                .cargo(profissionalRequest.cargo())
                .nascimento(profissionalRequest.nascimento())
                .build();
        when(profissionaisRepository.save(any(Profissional.class))).thenReturn(profissionalSalvo);
        when(profissionalMapper.toResponse(profissionalSalvo))
                .thenReturn(new ProfissionalResponse(1L, "John Doe", Cargo.DESENVOLVEDOR, new Date(), new Date()));

        ProfissionalResponse result = profissionaisService.cadastrarProfissional(profissionalRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("John Doe", result.getNome());
        assertEquals(Cargo.DESENVOLVEDOR, result.getCargo());
        assertNotNull(result.getNascimento());
        assertNotNull(result.getCreatedDate());
    }

    @Test
    void test_throw_exception_when_id_not_found() {
        Long id = 1L;
        ProfissionalRequest profissionalRequest = new ProfissionalRequest("John Doe", Cargo.DESENVOLVEDOR, new Date());
        when(profissionaisRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThrows(ProfissionalNaoEncontradoException.class,
                () -> profissionaisService.atualizarProfissional(id, profissionalRequest));
    }

    @Test
    void test_delete_valid_professional() {
        Long id = 1L;
        Profissional profissional = new Profissional();
        profissional.setId(id);
        profissional.setDeleted(false);

        when(profissionaisRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.of(profissional));

        profissionaisService.excluirProfissional(id);

        verify(profissionaisRepository).save(profissional);
        assertTrue(profissional.isDeleted());
    }

    @Test
    void test_throw_exception_if_id_is_null() {
        Long id = 123L;
        when(profissionaisRepository.findByIdAndDeletedFalse(id)).thenReturn(Optional.empty());

        assertThrows(ProfissionalNaoEncontradoException.class, () -> profissionaisService.excluirProfissional(id));
        verify(profissionaisRepository, times(0)).save(any(Profissional.class));
    }
}
