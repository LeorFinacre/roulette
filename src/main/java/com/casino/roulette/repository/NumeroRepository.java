package com.casino.roulette.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.casino.roulette.entity.Numero;

@RepositoryRestResource(exported = false)
public interface NumeroRepository extends JpaRepository<Numero, String> {

	Optional<Numero> findById(int id);

	@Query(value = "SELECT * FROM Numero WHERE valeur = ?1", nativeQuery = true)
	Numero findByValeur(String valeur);
}
