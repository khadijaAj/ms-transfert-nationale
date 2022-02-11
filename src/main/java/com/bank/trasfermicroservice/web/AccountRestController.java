package com.bank.trasfermicroservice.web;

import com.bank.trasfermicroservice.dtos.ClientProspectDTO;
import com.bank.trasfermicroservice.dtos.VirementRequestDto;
import com.bank.trasfermicroservice.dtos.VirementToSomeOneElse;
import com.bank.trasfermicroservice.entities.ClientProspec;
import com.bank.trasfermicroservice.entities.Transaction;
import com.bank.trasfermicroservice.service.ITransactionService;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping(path = "/transfert")
public class AccountRestController {
    private ITransactionService transactionService;

    public AccountRestController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PutMapping(path ="comptes/virement")
    public  void virement(@RequestBody VirementRequestDto virementRequestDto){
        transactionService.virementCompteToCompte(virementRequestDto);
    }
    @PutMapping(path ="comptes/virementmultiple")
    public  void virementmultiple(@RequestBody List<VirementRequestDto> virementRequestDto){
            transactionService.virementMultiple(virementRequestDto);
    }

    @PutMapping(path ="comptes/VirementCompteToEspace/{id}")
    public  void virementCompteToEspace(@RequestBody VirementRequestDto virement,@PathVariable Long id){
        transactionService.virementCompteToEspece(virement,id);
    }
    @PutMapping(path ="comptes/VirementEspeceToCompte/{id}")
    public  void virementEspeceToCompte(@RequestBody VirementRequestDto virement,@PathVariable Long id){
        transactionService.virementEspeceToCompte(virement,id);
    }
    @PutMapping(path ="comptes/VirementEpeceToEspece/{idDonneur}/{idRecepteur}")
    public  void virementEspeceToEspace(@RequestBody VirementRequestDto virement,@PathVariable Long idDonneur,@PathVariable Long idRecepteur){
        transactionService.virementEspeceTOEspece(virement,idDonneur,idRecepteur);
    }
    @PutMapping(path ="comptes/BlockTransaction/{id}")
    public  void virementmultiple(@PathVariable Long id){
        transactionService.blockTransaction(id);
    }
    @GetMapping(path = "/transferts/{id}")
    public Transaction getTransfertById(@PathVariable Long id){
        return transactionService.getTransfertById(id);
    }
    @GetMapping(path ="/transfers/")
    public List<Transaction> getAllTransferts(){
    	return transactionService.getAllTransferts();
    }
    
    
    @PostMapping(path ="/add")
    public  void addTransfert(@RequestBody VirementRequestDto VirementRequestDto){
        transactionService.saveTransfert(VirementRequestDto);
    }
    
    @GetMapping(path = "/clientProspec/")
    public List<ClientProspec> getALLClientProspects(){
        return transactionService.getAllClientProspects();
    }
    
    @GetMapping(path = "/clientProspec/{id}")
    public ClientProspec getClientProspecById(@PathVariable Long id){
        return transactionService.getClientProspectById(id);
    }
    
    @PostMapping(path ="/clientProspec/add")
    public  void addClientProspec(@RequestBody ClientProspectDTO ClientProspectDTO){
        transactionService.saveClientProspec(ClientProspectDTO);
    }
    
    @PutMapping(path ="/clientProspect/update/{id}")
    public  void addClientProspec(@PathVariable Long id, @RequestBody ClientProspectDTO ClientProspectDTO){
        transactionService.updateClientProspect(id, ClientProspectDTO);
    }
}
