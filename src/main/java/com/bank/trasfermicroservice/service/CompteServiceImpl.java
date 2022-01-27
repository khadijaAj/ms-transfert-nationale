package com.bank.trasfermicroservice.service;


import com.bank.trasfermicroservice.dtos.VirementRequestDto;
import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.repositories.CompteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public void virement(Long codeSource, Long codeDestination, double montant) {
        Compte c1=compteRepository.getById(codeSource);
        Compte  c2=compteRepository.getById(codeDestination);
        c1.setSolde(c1.getSolde()-montant);
        c2.setSolde(c2.getSolde()+montant);
        compteRepository.save(c1);
        compteRepository.save(c2);
    }

    @Override
    public void virementMultiple(List<VirementRequestDto> list) {
        for (VirementRequestDto virementRequestDto:
             list) {
            //virement(virementRequestDto.getCodeSource(),virementRequestDto.getCodeDestination(),virementRequestDto.getMontant());
        }
    }


}
