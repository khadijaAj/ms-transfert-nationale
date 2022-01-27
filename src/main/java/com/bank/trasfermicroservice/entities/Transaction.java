package com.bank.trasfermicroservice.entities;

import com.bank.trasfermicroservice.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ref_transfert;
    private Long idUser;
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




}
