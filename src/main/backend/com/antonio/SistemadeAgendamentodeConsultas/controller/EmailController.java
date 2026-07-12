package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/email")
@Tag(name = "E-mail", description = "Envio de e-mails com anexo")
public class EmailController { // Trabalha somente com as solicitações (GET, PUT, DELETE)

    @Autowired
    private EmailService emailService;


    @PostMapping(value = "/enviar", consumes = "multipart/form-data")
    @Operation(summary = "Enviar um e-mail pela API", description = "Enviar um e-mail pela API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao enviar o e-mail", content = @Content)
    })
    public ResponseEntity<String>enviar( // Recebe os dados do email
            @Parameter(description = "E-mail do destinatário", example = "paciente@email.com") @RequestParam String destinatario,
            @Parameter(description = "Assunto do e-mail", example = "Confirmação de consulta") @RequestParam String assunto,
            @Parameter(description = "Conteúdo/corpo do e-mail") @RequestParam String conteudo,
            @Parameter(description = "Arquivo anexo (máx. 5MB)") @RequestParam("anexo") MultipartFile anexo) {
        try {
            emailService.enviarEmail(destinatario, assunto, conteudo, anexo);
            return ResponseEntity.ok("E-mail enviado");
        } catch (Exception e ) {
            throw new RuntimeException("Erro inesperado ao enviar o e-mail\n" + e.getMessage());
        }
    }
}
