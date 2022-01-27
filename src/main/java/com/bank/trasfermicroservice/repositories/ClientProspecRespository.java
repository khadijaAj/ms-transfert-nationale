package com.bank.trasfermicroservice.repositories;

import com.bank.trasfermicroservice.entities.ClientProspec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientProspecRespository extends JpaRepository<ClientProspec,Long> {
}
