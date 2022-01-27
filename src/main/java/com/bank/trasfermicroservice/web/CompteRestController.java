package com.bank.trasfermicroservice.web;

import com.bank.trasfermicroservice.entities.Compte;
import com.bank.trasfermicroservice.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CompteRestController {

    private CompteRepository compteRepository;

    public CompteRestController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    @GetMapping(path = "/comptes")
    public List<Compte> listCompte()
    {
        return  compteRepository.findAll();
    }

    @GetMapping(path = "/comptes/{id}")
    public Compte getCompte(@PathVariable Long id)
    {
        return  compteRepository.findById(id).get();
    }
    @PostMapping(path = "/comptes")
    public Compte getCompte(@RequestBody Compte compte)
    {
        return  compteRepository.save(compte);

    }

    @PutMapping(path = "/comptes/{id}")
    public Compte updateCompte(@PathVariable Long id, @RequestBody Compte compte)
    {

        compte.setCode(id);
        return  compteRepository.save(compte);

    }
    @DeleteMapping(path = "/comptes/{id}")
    public void deleteCompte(@PathVariable Long id, @RequestBody Compte compte)
    {
          compteRepository.deleteById(id);

    }
}
