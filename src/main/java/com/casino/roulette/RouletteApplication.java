package com.casino.roulette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

import com.casino.roulette.controllers.NumeroController;
import com.casino.roulette.controllers.RouletteController;

@SpringBootApplication
@ComponentScan(basePackageClasses = RouletteController.class)
@ComponentScan(basePackageClasses = NumeroController.class)
public class RouletteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RouletteApplication.class, args);
	}

}
