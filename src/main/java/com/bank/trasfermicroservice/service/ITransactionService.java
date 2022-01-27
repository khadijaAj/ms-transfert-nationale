package com.bank.trasfermicroservice.service;

import com.bank.trasfermicroservice.dtos.VirementRequestDto;
import com.bank.trasfermicroservice.dtos.VirementToSomeOneElse;
import com.bank.trasfermicroservice.entities.ClientProspec;

import java.util.List;

public interface ITransactionService {
    void virementCompteToCompte(VirementRequestDto virementRequestDto);
    void virementMultiple(List<VirementRequestDto> list);
    void virementCompteToEspece(VirementRequestDto virement,Long id);
    void blockTransaction(Long id);
    void virementEspeceToCompte(VirementRequestDto virementRequestDto,Long id);
    void virementEspeceTOEspece(VirementRequestDto virementRequestDto,Long idDonneur,Long idRecipient);


}
