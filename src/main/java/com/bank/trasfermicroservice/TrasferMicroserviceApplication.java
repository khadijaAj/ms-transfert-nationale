package com.bank.trasfermicroservice;

import com.bank.trasfermicroservice.entities.ClientProspec;
import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.entities.Transaction;
import com.bank.trasfermicroservice.enums.State;
import com.bank.trasfermicroservice.enums.TypeCompte;
import com.bank.trasfermicroservice.repositories.ClientProspecRespository;
import com.bank.trasfermicroservice.repositories.CompteRepository;
import com.bank.trasfermicroservice.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class TrasferMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrasferMicroserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CompteRepository compteRepository, TransactionRepository transactionRepository, ClientProspecRespository clientProspecRespository)
	{	Compte c1=new Compte(null,20000,new Date(), TypeCompte.AGENT);
		return  args ->{
				compteRepository.save(c1);
				compteRepository.save(new Compte(null,400000,new Date(), TypeCompte.CLIENT));
				compteRepository.save(new Compte(null,200,new Date(), TypeCompte.CLIENT));
				compteRepository.save(new Compte(null,300000,new Date(), TypeCompte.CLIENT));
				compteRepository.save(new Compte(null,500,new Date(), TypeCompte.CLIENT));
				compteRepository.findAll().forEach(
						cp->{
							System.out.println(cp.getType());
							System.out.println(cp.getSolde());
						}
				);
				//transactionRepository.save(new Transaction(null,new Date(),"yassine chihab", State.TO_SERVE,100,c1));
				clientProspecRespository.save(new ClientProspec(null,"yasssine","chihab","222","33333"));
				clientProspecRespository.save(new ClientProspec(null,"khadija","ajermoune","555","33333"));


		};
	}
}
