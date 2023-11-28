package com.teste.contactsapi.repositories;

import com.teste.contactsapi.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatosRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByNomeContainsIgnoreCaseOrContatoContainsIgnoreCase(String nome, String contato);
}
