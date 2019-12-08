package com.casino.roulette;

import java.awt.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.casino.roulette.entity.Numero;
import com.casino.roulette.repository.NumeroRepository;

/**
 * @author Romain JOHAN
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

	private final NumeroRepository numeroRepository;

	@Autowired
	public DatabaseLoader(NumeroRepository numeroRepository) {
		this.numeroRepository = numeroRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
	}
}
