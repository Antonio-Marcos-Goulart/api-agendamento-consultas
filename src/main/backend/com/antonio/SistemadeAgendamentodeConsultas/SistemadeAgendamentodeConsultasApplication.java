package com.antonio.SistemadeAgendamentodeConsultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemadeAgendamentodeConsultasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemadeAgendamentodeConsultasApplication.class, args);
	}

}
