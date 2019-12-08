package com.casino.roulette.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casino.roulette.entity.Numero;
import com.casino.roulette.repository.NumeroRepository;

@RestController
@RequestMapping(path = "/numero")
public class NumeroController {
	
    @Autowired
    private NumeroRepository repository;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Numero> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/findByValeur/{valeur}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Numero findByValeur(@PathVariable("valeur") String valeur) {
        return repository.findByValeur(valeur);
    }
    
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Numero create(@RequestBody Numero numero) {
        return repository.save(numero);
    }
}
