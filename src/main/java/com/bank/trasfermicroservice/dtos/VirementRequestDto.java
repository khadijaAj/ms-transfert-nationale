package com.bank.trasfermicroservice.dtos;

import com.bank.trasfermicroservice.entities.Transaction;
import com.bank.trasfermicroservice.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class VirementRequestDto {
    private Long idUser;
    private String ref_transfert;
    private Long idDonneur;
    private Long idBeneficiaire;
    private String motif;
    private String typeFrais;
    private Date dateTransfert;
    private Date dateEmission;
    private Date dateServir;
    private String typeTransfert;
    private Boolean notification;
    private Boolean otp;
    @Enumerated(EnumType.STRING)
    private State status;
    private double amount;
   
    public void virementRequestDto(Transaction transaction) {
		
		
	}
}
