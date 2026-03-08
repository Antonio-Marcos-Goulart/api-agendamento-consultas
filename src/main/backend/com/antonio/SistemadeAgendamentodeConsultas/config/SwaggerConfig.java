package com.antonio.SistemadeAgendamentodeConsultas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AGENDAMENTO API REST")
                        .version("1.0.0")
                        .description("Documentação API REST AGENDAMENTO ")
                        .contact(new Contact()
                                .name("Antonio Marcos")
                                .email("antoniomarcos5674335@gmail.com")));
    }
}
