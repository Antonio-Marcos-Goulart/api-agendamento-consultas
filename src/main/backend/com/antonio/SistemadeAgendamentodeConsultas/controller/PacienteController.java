package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import com.antonio.SistemadeAgendamentodeConsultas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes", description = "Cadastro, consulta e gerenciamento de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Criar o cadastro do paciente", description = "Criar o cadastro do paciente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@Valid @RequestBody PacienteCreateDTO dto) {
        Paciente saved = pacienteService.createPaciente(dto);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Atualizar o paciente buscando pelo Id", description = "Atualizar o paciente buscando pelo Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(
            @Parameter(description = "Id do paciente", example = "1") @PathVariable Long id,
            @Valid @RequestBody PacienteCreateDTO dto) {
        return ResponseEntity.ok(pacienteService.updatePaciente(id, dto));
    }

    @Operation(summary = "Listar todos os pacientes", description = "Listar todos os pacientes")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso")
    @GetMapping
    public List<PacienteDTO> getAllPacientes() {
        return pacienteService.getAllPacientes()
                .stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar paciente por Id", description = "Buscar paciente por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(
            @Parameter(description = "Id do paciente", example = "1") @PathVariable Long id) {
        Paciente paciente = pacienteService.getPacienteById(id);
        return ResponseEntity.ok(new PacienteDTO(paciente));
    }

    @Operation(summary = "Deletar paciente por Id", description = "Deletar paciente por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(
            @Parameter(description = "Id do paciente", example = "1") @PathVariable Long id) {
        pacienteService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca pacientes por id, cpf ou nome", description = "Busca pacientes por id, cpf ou nome. Todos os filtros são opcionais e combináveis")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @GetMapping("/search")
    public List<PacienteDTO> searchPacientes(
            @Parameter(description = "Id exato do paciente") @RequestParam(required = false) Long id,
            @Parameter(description = "CPF (11 dígitos, sem pontuação)") @RequestParam(required = false) String cpf,
            @Parameter(description = "Nome ou parte do nome do paciente") @RequestParam(required = false) String nome) {
        return pacienteService.buscarPaciente(id, cpf, nome)
                .stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }
}

