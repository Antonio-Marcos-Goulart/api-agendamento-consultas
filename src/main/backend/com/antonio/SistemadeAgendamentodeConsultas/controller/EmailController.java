package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/email")
public class EmailController { // Trabalha somente com as solicitações (GET, PUT, DELETE)

    @Autowired
    private EmailService emailService;


    @PostMapping(value = "/enviar", consumes = "multipart/form-data")
    @Operation(summary = "Enviar um e-mail pela API", description = "Enviar um e-mail pela API")

    public ResponseEntity<String>enviar( // Recebe os dados do email
            @RequestParam String destinatario,
            @RequestParam String assunto,
            @RequestParam String conteudo,
            @RequestParam("anexo") MultipartFile anexo) {
        try {
            emailService.enviarEmail(destinatario, assunto, conteudo, anexo);
            return ResponseEntity.ok("E-mail enviado");
        } catch (Exception e ) {
            throw new RuntimeException("Erro inesperado ao enviar o e-mail\n" + e.getMessage());
        }
    }
}
