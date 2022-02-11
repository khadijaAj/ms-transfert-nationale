package com.bank.trasfermicroservice.dtos;

import com.bank.trasfermicroservice.entities.ClientProspec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ClientProspectDTO {
	 	private  String nom;
	    private  String prenom;
	    private  String cin;
	    private  String Telephone;
}
