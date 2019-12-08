package com.casino.roulette.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casino.roulette.entity.Numero;
import com.casino.roulette.entity.NumeroOccurence;
import com.casino.roulette.entity.Tirage;
import com.casino.roulette.repository.NumeroRepository;
import com.casino.roulette.repository.TirageRepository;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@RestController
@RequestMapping(path = "/tirage")
public class TirageController {
	
    @Autowired
    private TirageRepository tirageRepository;
    
    @Autowired
    private NumeroRepository numeroRepository;

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Tirage> findAllLimit12() {
    	List<Tirage> tirages = tirageRepository.findAll().subList(Math.max(tirageRepository.findAll().size() - 12, 0), tirageRepository.findAll().size());
    	
        return tirages;
    }

    @GetMapping(path = "/list-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Tirage> findAll() {
        return tirageRepository.findAll();
    }
    
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Tirage create(@RequestBody Tirage tirage) {
    	Tirage t = new Tirage();
    	t.setValeur(tirage.getValeur());
    	t.setCouleur(tirage.getCouleur());
        return tirageRepository.save(t);
    }
    
    @PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody Tirage tirage) {
    	tirageRepository.delete(tirage);
    }
    
    @GetMapping(path = "/list-number-graphic", produces = MediaType.APPLICATION_JSON_VALUE)
    public Multimap<Integer, String> findGraphicNumbers() {
    	List<Numero> numeros = numeroRepository.findAll();
    	List<Tirage> tirages = tirageRepository.findAll();
    	Multimap<Integer, String> occurences = ArrayListMultimap.create();
    	for(int i = 0; i<numeros.size(); i++) {
    		int occurence = 0;
    		for(int j = 0; j<tirages.size(); j++) {
    			if(numeros.get(i).getValeur().equals(tirages.get(j).getValeur())) {
    				occurence++;
    			}
    		}
    		NumeroOccurence numOccur = new NumeroOccurence();
    		numOccur.setValeur(numeros.get(i).getValeur());
    		numOccur.setOccurence(occurence);
    		occurences.put(numOccur.getOccurence(), numOccur.getValeur());
    	}
    	List<String> numerosByOccurence = new ArrayList<>(occurences.values());
    	System.out.println(numerosByOccurence);
    	return occurences;
    }
    
    @GetMapping(path = "/list-hot-cold", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Numero> findHotColdNumbers() {
    	List<Numero> numeros = numeroRepository.findAll();
    	List<Tirage> tirages = tirageRepository.findAll();
    	Multimap<Integer, NumeroOccurence> occurences = ArrayListMultimap.create();
    	for(int i = 0; i<numeros.size(); i++) {
    		int occurence = 0;
    		for(int j = 0; j<tirages.size(); j++) {
    			if(numeros.get(i).getValeur().equals(tirages.get(j).getValeur())) {
    				occurence++;
    			}
    		}
    		NumeroOccurence numOccur = new NumeroOccurence();
    		numOccur.setValeur(numeros.get(i).getValeur());
    		numOccur.setOccurence(occurence);
    		occurences.put(numOccur.getOccurence(), numOccur);
    	}
    	List<NumeroOccurence> numerosByOccurence = new ArrayList<>(occurences.values());
    	Collections.sort(numerosByOccurence, Comparator.comparing(NumeroOccurence::getOccurence));
    	List<Numero> numerosHot = new ArrayList<Numero>();
    	for(NumeroOccurence num : numerosByOccurence) {
    		numerosHot.add(numeroRepository.findByValeur(num.getValeur()));
    		System.out.println("["+num.getValeur()+"] --->"+num.getOccurence());
    	}
    	return numerosHot;
    }
}
