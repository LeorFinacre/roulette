package com.casino.roulette.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tirage {

	@Id
	private int id;
	private String valeur;
	
	private String couleur;
}
