package com.antonio.SistemadeAgendamentodeConsultas.unit;

import com.antonio.SistemadeAgendamentodeConsultas.SistemadeAgendamentodeConsultasApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

class SistemadeAgendamentodeConsultasApplicationTests {

	@Test
	void applicationClassMantemAnotacoesPrincipais() {
		org.assertj.core.api.Assertions.assertThat(
				SistemadeAgendamentodeConsultasApplication.class.isAnnotationPresent(SpringBootApplication.class)
		).isTrue();
		org.assertj.core.api.Assertions.assertThat(
				SistemadeAgendamentodeConsultasApplication.class.isAnnotationPresent(EnableScheduling.class)
		).isTrue();
	}

}
