package com.bank.trasfermicroservice.repositories;

import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long> {
    @RestResource(path = "/byType")
    List<Compte> findByType(TypeCompte type);
}

