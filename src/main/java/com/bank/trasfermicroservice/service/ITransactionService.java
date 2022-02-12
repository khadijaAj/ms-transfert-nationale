package com.bank.trasfermicroservice.service;

import com.bank.trasfermicroservice.dtos.ClientProspectDTO;
import com.bank.trasfermicroservice.dtos.VirementRequestDto;
import com.bank.trasfermicroservice.dtos.VirementToSomeOneElse;
import com.bank.trasfermicroservice.entities.ClientProspec;
import com.bank.trasfermicroservice.entities.Transaction;

import java.util.List;

public interface ITransactionService {
    void virementCompteToCompte(VirementRequestDto virementRequestDto);
    void virementMultiple(List<VirementRequestDto> list);
    void virementCompteToEspece(VirementRequestDto virement,Long id);
    void blockTransaction(Long id);
    void virementEspeceToCompte(VirementRequestDto virementRequestDto,Long id);
    void virementEspeceTOEspece(VirementRequestDto virementRequestDto,Long idDonneur,Long idRecipient);
    Transaction getTransfertById(Long id);
    void saveTransfert(VirementRequestDto VirementRequestDto);
    ClientProspec getClientProspectById(Long id);
    List<ClientProspec> getAllClientProspects();
    List<Transaction> getAllTransferts();
    void saveClientProspec(ClientProspectDTO ClientProspectDTO);
    void deleteClientProspec(Long id);
    void updateClientProspect(Long id, ClientProspectDTO ClientProspectDTO);
    List<ClientProspec> getClientProspecByGSM(String Telephone);

}
