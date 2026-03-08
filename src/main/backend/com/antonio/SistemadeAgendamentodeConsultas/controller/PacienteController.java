package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import com.antonio.SistemadeAgendamentodeConsultas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @Operation(summary = "Criar o cadastro do paciente", description = "Criar o cadastro do paciente")
    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@Valid @RequestBody PacienteCreateDTO dto) {
        Paciente saved = pacienteService.createPaciente(dto);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Atualizar o paciente buscando pelo Id", description = "Atualizar o paciente buscando pelo Id")
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Long id, @Valid @RequestBody PacienteCreateDTO dto) {
        return ResponseEntity.ok(pacienteService.updatePaciente(id, dto));
    }

    @Operation(summary = "Listar todos os pacientes", description = "Listar todos os pacientes")
    @GetMapping
    public List<PacienteDTO> getAllPacientes() {
        return pacienteService.getAllPacientes()
                .stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar paciente por Id", description = "Buscar paciente por Id")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable Long id) {
        Paciente paciente = pacienteService.getPacienteById(id);
        return ResponseEntity.ok(new PacienteDTO(paciente));
    }

    @Operation(summary = "Deletar paciente por Id", description = "Deletar paciente por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca pacientes por id, cpf ou nome", description = "Busca pacientes por id, cpf ou nome")
    @GetMapping("/search")
    public List<PacienteDTO> searchPacientes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome) {
        return pacienteService.searchPaciente(id, cpf, nome)
                .stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }
}

