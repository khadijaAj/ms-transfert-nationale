package com.bank.trasfermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class VirementToSomeOneElse {
    private Long codeSource;
    String ReferenceVirment;
    String cin;
    String phoneNumber;
    private double montant;
}
