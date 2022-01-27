package com.bank.trasfermicroservice.service;

import com.bank.trasfermicroservice.dtos.VirementRequestDto;

import java.util.List;

public interface CompteService {
      void virement(Long codeSource,Long codeDestination,double montant);
      void virementMultiple(List<VirementRequestDto> list);

}
