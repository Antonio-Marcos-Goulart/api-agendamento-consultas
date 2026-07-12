package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento.AgendamentoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
@Tag(name = "Agendamentos", description = "Criação e cancelamento de agendamentos de consulta")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Criar um agendamento", description = "Criar um agendamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente ou médico informado não foi encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno ao processar o agendamento", content = @Content)
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> agendar(@Valid @RequestBody AgendamentoCreateDTO dto) {
        try {
            agendamentoService.agendar(dto);
            return ResponseEntity
                    .status(201)
                    .body("Agendamento criado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(404)
                    .body("Recurso não encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar agendamento por Id", description = "Deletar agendamento por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteAgendamento(
            @Parameter(description = "Id do agendamento", example = "1") @PathVariable Long id){
        agendamentoService.deletarAgendamento(id);
    }
}

// PROBLEMAS COM A DATA DDE AGENDAMENTO, SEMPRE COLOCAR NESSE FORMATO: "dataAgendamento": "07/04/2026 03:26:21",