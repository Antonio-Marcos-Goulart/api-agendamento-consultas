package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento.AgendamentoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Criar um agendamento", description = "Criar um agendamento")
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
    @DeleteMapping("/{id}")
    public void deleteAgendamento(@PathVariable Long id){
        agendamentoService.deletarAgendamento(id);
    }
}

// PROBLEMAS COM A DATA DDE AGENDAMENTO, SEMPRE COLOCAR NESSE FORMATO: "dataAgendamento": "07/04/2026 03:26:21",