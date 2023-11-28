package com.teste.contactsapi.repositories;

import com.teste.contactsapi.domain.Cargo;
import com.teste.contactsapi.domain.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByIdAndDeletedIs(Long id, boolean deleted);


    List<Profissional> findByDeletedFalseAndNomeContainsIgnoreCaseOrCargoIn(String nome, Collection<Cargo> cargos);




}
