package com.bank.trasfermicroservice.entities;

import com.bank.trasfermicroservice.enums.TypeCompte;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private  double solde;
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private TypeCompte type;

}
