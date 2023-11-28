package com.teste.contactsapi.repositories;

import com.teste.contactsapi.domain.Cargo;
import com.teste.contactsapi.domain.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByIdAndDeletedFalse(Long id);

    @Query("""
            select p from profissionais p
            where p.deleted = false
                and upper(p.nome) like upper(concat('%', ?1, '%'))
                or p.cargo in ?2
            """)
    List<Profissional> findByRequestedString(String nome, Collection<Cargo> cargos);




}
