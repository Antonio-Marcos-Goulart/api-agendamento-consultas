package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico.MedicoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico.MedicoDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.service.MedicoService;
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

@RestController
@RequestMapping("medico")
@Tag(name = "Médicos", description = "Cadastro, consulta e gerenciamento de médicos")
public class MedicoController {

    private MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar médico", description = "Cadastrar médico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<MedicoDTO> createMedico(@Valid @RequestBody MedicoCreateDTO dto) {
        Medico saved = medicoService.createMedico(dto);
        return ResponseEntity.ok(new MedicoDTO(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico buscando por Id", description = "Atualizar médico buscando por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content)
    })
    public ResponseEntity<MedicoDTO> updateMedico(
            @Parameter(description = "Id do médico", example = "1") @PathVariable Long id,
            @Valid @RequestBody MedicoCreateDTO dto) {
        Medico updated = medicoService.updateMedico(id, dto);
        return ResponseEntity.ok(new MedicoDTO(updated));
    }

    @GetMapping
    @Operation(summary = "Listar todos os médicos", description = "Listar todos os médicos")
    @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso")
    public List<Medico> getAllMedicos() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por Id", description = "Buscar médico por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content)
    })
    public Medico getMedicoById(
            @Parameter(description = "Id do médico", example = "1") @PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar médico por Id", description = "Deletar médico por Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Médico deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content)
    })
    public void deleteMedico(
            @Parameter(description = "Id do médico", example = "1") @PathVariable Long id){
        medicoService.deleteMedico(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Busca medicos por id, cpf, nome ou CRM", description = "Busca medicos por id, cpf, nome ou CRM. Todos os filtros são opcionais e combináveis")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public List<Medico> buscarMedicos(
            @Parameter(description = "Id exato do médico") @RequestParam(required = false) Long id,
            @Parameter(description = "CPF (11 dígitos, sem pontuação)") @RequestParam(required = false) String cpf,
            @Parameter(description = "Nome ou parte do nome do médico") @RequestParam(required = false) String nome,
            @Parameter(description = "CRM no formato 123456-UF") @RequestParam(required = false) String crm){
        return medicoService.buscarMedico(id, cpf, nome, crm);
    }
}
