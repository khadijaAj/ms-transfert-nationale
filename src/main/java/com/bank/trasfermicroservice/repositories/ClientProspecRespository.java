package com.bank.trasfermicroservice.repositories;

import com.bank.trasfermicroservice.entities.ClientProspec;
import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.enums.TypeCompte;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ClientProspecRespository extends JpaRepository<ClientProspec,Long> {
	
    List<ClientProspec> findByTelephone(String telephone);
	
}
