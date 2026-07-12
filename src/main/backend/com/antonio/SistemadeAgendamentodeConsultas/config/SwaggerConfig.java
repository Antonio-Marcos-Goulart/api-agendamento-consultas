package com.antonio.SistemadeAgendamentodeConsultas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agendamento de Consultas API")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de pacientes, médicos e agendamentos de consultas.

                                Recursos disponíveis:
                                - Cadastro, busca e atualização de pacientes e médicos
                                - Criação e cancelamento de agendamentos
                                - Envio de e-mails com anexo
                                """)
                        .contact(new Contact()
                                .name("Antonio Marcos")
                                .email("antoniomarcos5674335@gmail.com"))
                        .license(new License()
                                .name("Uso interno")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Ambiente local")
                ));
    }
}
