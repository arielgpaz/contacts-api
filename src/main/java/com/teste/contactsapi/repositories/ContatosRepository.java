package com.teste.contactsapi.repositories;

import com.teste.contactsapi.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatosRepository extends JpaRepository<Contato, Long> {

    @Query("""
            select c from contatos c
            where upper(c.nome) like upper(concat('%', ?1, '%'))
                or upper(c.contato) like upper(concat('%', ?2, '%'))
            """)
    List<Contato> findByRequestedString(String nome, String contato);
}
