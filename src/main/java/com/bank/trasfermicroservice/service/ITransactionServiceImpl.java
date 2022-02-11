package com.bank.trasfermicroservice.service;

import com.bank.trasfermicroservice.dtos.ClientProspectDTO;
import com.bank.trasfermicroservice.dtos.VirementRequestDto;
import com.bank.trasfermicroservice.dtos.VirementToSomeOneElse;
import com.bank.trasfermicroservice.entities.ClientProspec;
import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.entities.Transaction;
import com.bank.trasfermicroservice.enums.State;
import com.bank.trasfermicroservice.exceptions.ApiRequestException;
import com.bank.trasfermicroservice.repositories.ClientProspecRespository;
import com.bank.trasfermicroservice.repositories.CompteRepository;
import com.bank.trasfermicroservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ITransactionServiceImpl implements ITransactionService {

    private  CompteRepository compteRepository;
    private TransactionRepository transactionRepository;
    private ClientProspecRespository clientProspecRespository;
    private boolean verifMontantClient;
    private boolean verifMontantAgence;

    public ITransactionServiceImpl(CompteRepository compteRepository, TransactionRepository transactionRepository, ClientProspecRespository clientProspecRespository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
        this.clientProspecRespository = clientProspecRespository;
    }




    @Override
    public void virementCompteToCompte(VirementRequestDto virementRequestDto) {
        Calendar date=Calendar.getInstance();
        String ref=generateRefTransfert("CLT_");
        long actualDate=date.getTimeInMillis();
        Date dateServir=new Date(actualDate+(60*1000));
        System.out.println(dateServir);
        // TODO: 10/01/2022 from compte microservice
        Compte c1=compteRepository.findById(virementRequestDto.getIdDonneur()).orElseThrow(()->new ApiRequestException("compte not found "));
        Compte  c2=compteRepository.findById(virementRequestDto.getIdBeneficiaire()).orElseThrow(()->new ApiRequestException("compte not found "));

        // TODO: 10/01/2022  Test
        UpdateAccount(virementRequestDto, c1, c2);

        // TODO: 10/01/2022  callback
        if(new Date()==dateServir) {
            compteRepository.save(c1);

            compteRepository.save(c2);
        }
        Transaction transaction=new Transaction();
        transaction.setDateTransfert(new Date());
        transaction.setStatus(getEtatTransfert(virementRequestDto.getAmount(),virementRequestDto.getOtp()));
        transaction.setAmount(virementRequestDto.getAmount());
        transaction.setRef_transfert(ref);
        transaction.setTypeTransfert(virementRequestDto.getTypeTransfert());
        transaction.setIdDonneur(virementRequestDto.getIdDonneur());
        transaction.setIdBeneficiaire(virementRequestDto.getIdBeneficiaire());
        transaction.setDateServir(dateServir);
        transaction.setOtp(virementRequestDto.getOtp());
        transaction.setNotification(virementRequestDto.getNotification());
        transaction.setMotif(virementRequestDto.getMotif());
        transactionRepository.save(transaction);

    }

    @Override
    public void virementEspeceToCompte(VirementRequestDto virementRequestDto,Long id)
    {
        Calendar date=Calendar.getInstance();
        String ref=generateRefTransfert("AGE_");
        long actualDate=date.getTimeInMillis();
        Date dateServir=new Date(actualDate+(2*60*1000));
        // TODO: 10/01/2022 from compte microservice

        Compte  c2=compteRepository.findById(virementRequestDto.getIdBeneficiaire()).orElseThrow(()->new ApiRequestException("compte not found "));;
        Compte compteAgent=compteRepository.findById(virementRequestDto.getIdUser()).orElseThrow(()->new ApiRequestException("compte not found "));;
        // TODO: 10/01/2022  Test
        UpdateAccountV2(virementRequestDto, c2,compteAgent);

        // TODO: 10/01/2022  callback
        if(new Date()==dateServir) {

            compteRepository.save(c2);
        }


        Transaction transaction=new Transaction();
        transaction.setDateTransfert(new Date());
        transaction.setStatus(getEtatTransfert(virementRequestDto.getAmount(),virementRequestDto.getOtp()));
        transaction.setAmount(virementRequestDto.getAmount());
        transaction.setRef_transfert(ref);
        transaction.setIdDonneur(virementRequestDto.getIdDonneur());
        transaction.setIdBeneficiaire(virementRequestDto.getIdBeneficiaire());
        transaction.setDateServir(dateServir);
        transaction.setTypeFrais(virementRequestDto.getTypeFrais());
        transaction.setIdUser(virementRequestDto.getIdUser());
        transaction.setTypeTransfert(virementRequestDto.getTypeTransfert());
        transaction.setOtp(virementRequestDto.getOtp());
        transaction.setNotification(virementRequestDto.getNotification());
        transaction.setMotif(virementRequestDto.getMotif());
        transactionRepository.save(transaction);



    }
    public Integer getFrais(double montant) {
        if(montant >=100 && montant <= 1000) return Integer.valueOf(30);

        return Integer.valueOf(49);

    }
    public Boolean verifMontantClient(double montant) {
    //    Boolean verifSoldeAnn = verifMontantAnnuelClient(idDonneur);
       // Boolean LimitQuotidienneClient = clientProxy.limitQuotidienneClient(idDonneur);

        if(montant > 2000 ) {
            return false;

        }
        else {
            return true;
        }
    }
    public Boolean verifMontantAgence(double montant) {
        //Boolean LimitQuotidienneAgence = clientProxy.limitQuotidinneAgence(idAgence);
        if(montant > 80000) {
            return false;

        }
        else {
            return true;
        }
    }
    public State getEtatTransfert(double montant,boolean otp) {
        State etat;

        if(verifMontantClient(montant) == false || otp == false || verifMontantAgence(montant) == false ) {
            etat = State.REtURNED;
        }
        else {
            etat= State.TO_SERVE;
        }
        return etat;
    }
   /* public Boolean verifMontantAnnuelClient(Long idDonneur) {
        ClientBean client = clientProxy.clientByTypeById(idDonneur);
        if(client.getSoldeAnnTransfert() <= 20000) {
            return true;
        }
        else {
            return false;
        }
    }*/
    public String generateRefTransfert(String debut) {
        Random random = new Random();
        long p = random.nextLong();
        String ref_transf = debut + Long.toString(p);
        return ref_transf;

    }

    private void UpdateAccount(VirementRequestDto virementRequestDto, Compte c1, Compte c2) {

        System.out.println(virementRequestDto.getTypeFrais());
        if(virementRequestDto.getTypeFrais().equals("ByClient"))
        {
            c1.setSolde(c1.getSolde()- virementRequestDto.getAmount()-getFrais(virementRequestDto.getAmount()));
            c2.setSolde(c2.getSolde()+ virementRequestDto.getAmount());
            System.out.println("***********");
        }
        else
        {
            c1.setSolde(c1.getSolde()- virementRequestDto.getAmount());
            c2.setSolde(c2.getSolde()+ virementRequestDto.getAmount()-getFrais(virementRequestDto.getAmount()));
        }




    }
    private void UpdateAccountV2(VirementRequestDto virementRequestDto, Compte c,Compte compteAgent) {

        System.out.println(virementRequestDto.getTypeFrais());
        if(virementRequestDto.getTypeFrais().equals("ByClient"))
        {

            c.setSolde(c.getSolde()+ virementRequestDto.getAmount());
            System.out.println("***********");
        }
        else
        {

            c.setSolde(c.getSolde()+ virementRequestDto.getAmount()-getFrais(virementRequestDto.getAmount()));
        }
        compteAgent.setSolde(compteAgent.getSolde()+getFrais(virementRequestDto.getAmount()));


    }

    private void UpdateAccountV3(VirementRequestDto virementRequestDto, Compte c,Compte compteAgent) {

        System.out.println(virementRequestDto.getTypeFrais());
        if(virementRequestDto.getTypeFrais().equals("ByClient"))
        {

            c.setSolde(c.getSolde()- virementRequestDto.getAmount()-getFrais(virementRequestDto.getAmount()));
            System.out.println("***********");
        }

        compteAgent.setSolde(compteAgent.getSolde()+getFrais(virementRequestDto.getAmount()));


    }

    @Override
    public void virementMultiple(List<VirementRequestDto> list) {
        for (VirementRequestDto virementRequestDto:
                list) {
            virementCompteToCompte(virementRequestDto);
        }
    }

    @Override
    public void virementCompteToEspece(VirementRequestDto virementRequestDto,Long id){
        Calendar date=Calendar.getInstance();
        String ref=generateRefTransfert("CLE_");
        long actualDate=date.getTimeInMillis();
        Date dateServir=new Date(actualDate+(2*60*1000));
        // TODO: 10/01/2022 from compte microservice
        ClientProspec clientProspec=clientProspecRespository.findById(id).orElseThrow(()->new ApiRequestException("client not found "));;
        Compte  c2=compteRepository.findById(virementRequestDto.getIdDonneur()).orElseThrow(()->new ApiRequestException("compte not found "));;
        Compte compteAgent=compteRepository.findById(virementRequestDto.getIdUser()).orElseThrow(()->new ApiRequestException("compte not found "));;
        // TODO: 10/01/2022  Test
        UpdateAccountV3(virementRequestDto, c2,compteAgent);

        // TODO: 10/01/2022  callback
        if(new Date()==dateServir) {

            compteRepository.save(c2);
        }
        Transaction transaction=new Transaction();
        transaction.setDateTransfert(new Date());
        transaction.setStatus(getEtatTransfert(virementRequestDto.getAmount(),virementRequestDto.getOtp()));
        transaction.setAmount(virementRequestDto.getAmount());
        transaction.setRef_transfert(ref);
        transaction.setIdDonneur(clientProspec.getId());
        transaction.setIdBeneficiaire(virementRequestDto.getIdBeneficiaire());
        transaction.setDateServir(dateServir);
        transaction.setTypeFrais(virementRequestDto.getTypeFrais());
        transaction.setIdUser(virementRequestDto.getIdUser());
        transaction.setTypeTransfert(virementRequestDto.getTypeTransfert());
        transaction.setOtp(virementRequestDto.getOtp());
        transaction.setNotification(virementRequestDto.getNotification());
        transaction.setMotif(virementRequestDto.getMotif());
        transactionRepository.save(transaction);



    }

    @Override
    public void blockTransaction(Long id) {
        Transaction transaction=transactionRepository.getById(id);
        transaction.setStatus(State.BLOCKED);
        transactionRepository.save(transaction);
    }



    @Override
    public void virementEspeceTOEspece(VirementRequestDto virementRequestDto,Long idDonneur,Long idRecipient) {

        Calendar date=Calendar.getInstance();
        String ref=generateRefTransfert("CLE_");
        long actualDate=date.getTimeInMillis();
        Date dateServir=new Date(actualDate+(2*60*1000));
        // TODO: 10/01/2022 from compte microservice
        ClientProspec clientProspecDonneur=clientProspecRespository.findById(idDonneur).orElseThrow(()->new ApiRequestException("client not found "));;
        ClientProspec clientProspecRecipient=clientProspecRespository.findById(idRecipient).orElseThrow(()->new ApiRequestException("client not found "));;
        Compte compteAgent=compteRepository.findById(virementRequestDto.getIdUser()).orElseThrow(()->new ApiRequestException("compte not found "));;
        // TODO: 10/01/2022  Test
        compteAgent.setSolde(compteAgent.getSolde()+getFrais(virementRequestDto.getAmount()));

        // TODO: 10/01/2022  callback


        Transaction transaction=new Transaction();
        transaction.setDateTransfert(new Date());
        transaction.setStatus(getEtatTransfert(virementRequestDto.getAmount(),virementRequestDto.getOtp()));
        transaction.setAmount(virementRequestDto.getAmount());
        transaction.setRef_transfert(ref);
        transaction.setIdDonneur(clientProspecDonneur.getId());
        transaction.setIdBeneficiaire(clientProspecRecipient.getId());
        transaction.setDateServir(dateServir);
        transaction.setTypeFrais(virementRequestDto.getTypeFrais());
        transaction.setIdUser(virementRequestDto.getIdUser());
        transaction.setTypeTransfert(virementRequestDto.getTypeTransfert());
        transaction.setOtp(virementRequestDto.getOtp());
        transaction.setNotification(virementRequestDto.getNotification());
        transaction.setMotif(virementRequestDto.getMotif());
        transactionRepository.save(transaction);


    }




	@Override
	public Transaction getTransfertById(Long id) {
		return transactionRepository.findById(id).get();
	
	}




	@Override
	public void saveTransfert(VirementRequestDto virementRequestDto) {
		Transaction transaction=new Transaction();
        transaction.setDateTransfert(new Date());
        transaction.setStatus(getEtatTransfert(virementRequestDto.getAmount(),virementRequestDto.getOtp()));
        transaction.setAmount(virementRequestDto.getAmount());
        transaction.setRef_transfert(virementRequestDto.getRef_transfert());
        transaction.setIdDonneur(virementRequestDto.getIdDonneur());
        transaction.setIdBeneficiaire(virementRequestDto.getIdBeneficiaire());
        transaction.setDateServir(virementRequestDto.getDateServir());
        transaction.setTypeFrais(virementRequestDto.getTypeFrais());
        transaction.setIdUser(virementRequestDto.getIdUser());
        transaction.setTypeTransfert(virementRequestDto.getTypeTransfert());
        transaction.setOtp(virementRequestDto.getOtp());
        transaction.setNotification(virementRequestDto.getNotification());
        transaction.setMotif(virementRequestDto.getMotif());
        transactionRepository.save(transaction);
		
	}




	@Override
	public ClientProspec getClientProspectById(Long id) {
		// TODO Auto-generated method stub
		return clientProspecRespository.findById(id).get();
	}




	@Override
	public void saveClientProspec(com.bank.trasfermicroservice.dtos.ClientProspectDTO ClientProspectDTO) {
		ClientProspec clientProspec = new ClientProspec();
		clientProspec.setCin(ClientProspectDTO.getCin());
		clientProspec.setNom(ClientProspectDTO.getNom());
		clientProspec.setPrenom(ClientProspectDTO.getPrenom());
		clientProspec.setTelephone(ClientProspectDTO.getTelephone());
		clientProspecRespository.save(clientProspec);
	}




	@Override
	public List<ClientProspec> getAllClientProspects() {
		
		return clientProspecRespository.findAll() ;
	}




	@Override
	public void deleteClientProspec(Long id) {
		
	}




	
	@Override
	public void updateClientProspect(Long id, ClientProspectDTO ClientProspectDTO) {
		ClientProspec clientProsp = new ClientProspec();
		clientProsp.setId(id);
		clientProsp.setNom(ClientProspectDTO.getNom());
		clientProsp.setCin(ClientProspectDTO.getCin());
		clientProsp.setPrenom(ClientProspectDTO.getPrenom());
		clientProsp.setTelephone(ClientProspectDTO.getTelephone());
		clientProspecRespository.save(clientProsp);
		
	}




	@Override
	public List<Transaction> getAllTransferts() {
		return transactionRepository.findAll();
	}
}
