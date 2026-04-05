package com.antonio.SistemadeAgendamentodeConsultas;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemadeAgendamentodeConsultasApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        applyPropertyIfMissing("DB_URL", dotenv);
        applyPropertyIfMissing("DB_USERNAME", dotenv);
        applyPropertyIfMissing("DB_PASSWORD", dotenv);

        applyPropertyIfMissing("SMTP_MAIL", dotenv);
        applyPropertyIfMissing("SMTP_PORT", dotenv);
        applyPropertyIfMissing("SMTP_USERNAME", dotenv);
        applyPropertyIfMissing("SMTP_PASSWORD", dotenv);

		SpringApplication.run(SistemadeAgendamentodeConsultasApplication.class, args);
	}

    private static void applyPropertyIfMissing(String key, Dotenv dotenv) {
        if (System.getProperty(key) != null || System.getenv(key) != null) {
            return;
        }

        String value = dotenv.get(key);
        if (value != null && !value.isBlank()) {
            System.setProperty(key, value);
        }
    }

}
