package com.casino.roulette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.casino.roulette.entity.Tirage;


@RepositoryRestResource(exported = false)
public interface TirageRepository extends JpaRepository<Tirage, String> {
	
}
