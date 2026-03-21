package com.antonio.SistemadeAgendamentodeConsultas;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemadeAgendamentodeConsultasApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        //SMTP
        System.setProperty("SMTP_MAIL", dotenv.get("SMTP_MAIL"));
        System.setProperty("SMTP_PORT", dotenv.get("SMTP_PORT"));
        System.setProperty("SMTP_USERNAME", dotenv.get("SMTP_USERNAME"));
        System.setProperty("SMTP_PASSWORD", dotenv.get("SMTP_PASSWORD"));
		SpringApplication.run(SistemadeAgendamentodeConsultasApplication.class, args);
	}

}
