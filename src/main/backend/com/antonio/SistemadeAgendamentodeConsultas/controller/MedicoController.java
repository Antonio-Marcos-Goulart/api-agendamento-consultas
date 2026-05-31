package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico.MedicoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico.MedicoDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medico")
public class MedicoController {

    private MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar médico", description = "Cadastrar médico")
    public ResponseEntity<MedicoDTO> createMedico(@Valid @RequestBody MedicoCreateDTO dto) {
        Medico saved = medicoService.createMedico(dto);
        return ResponseEntity.ok(new MedicoDTO(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico buscando por Id", description = "Atualizar médico buscando por Id")
    public ResponseEntity<MedicoDTO> updateMedico(@PathVariable Long id, @Valid @RequestBody MedicoCreateDTO dto) {
        Medico updated = medicoService.updateMedico(id, dto);
        return ResponseEntity.ok(new MedicoDTO(updated));
    }

    @GetMapping
    @Operation(summary = "Listar todos os médicos", description = "Listar todos os médicos")
    public List<Medico> getAllMedicos() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por Id", description = "Buscar médico por Id")
    public Medico getMedicoById(@PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar médico por Id", description = "Deletar médico por Id")
    public void deleteMedico(@PathVariable Long id){
        medicoService.deleteMedico(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Busca medicos por id, cpf, nome ou CRM", description = "Busca medicos por id, cpf, nome ou CRM")
    public List<Medico> searchMedico(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String crm){
        return medicoService.searchMedico(id, cpf, nome, crm);
    }
}
