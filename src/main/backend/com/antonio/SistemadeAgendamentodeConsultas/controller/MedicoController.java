package com.antonio.SistemadeAgendamentodeConsultas.controller;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
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
    public Medico createMedico(@RequestBody Medico medico){
        return medicoService.createMedico(medico);
    }

    @GetMapping
    @Operation(summary = "Listar todos os médicos", description = "Listar todos os médicos")
    public List<Medico> getAllMedicos() {
        return medicoService.getAllMedicos();
    }

    @Operation(summary = "Buscar médico por Id", description = "Buscar médico por Id")
    @GetMapping("/{id}")
    public Medico getMedicoById(@PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    @Operation(summary = "Atualizar médico buscando por Id", description = "Atualizar médico buscando por Id")
    @PutMapping("/{id}")
    public Medico updateMedico(@PathVariable Long id, @RequestBody Medico updatedMedico) {
        return medicoService.updateMedico(id, updatedMedico);
    }

    @Operation(summary = "Deletar médico por Id", description = "Deletar médico por Id")
    @DeleteMapping("/{id}")
    public void deleteMedico(@PathVariable Long id){
        medicoService.deleteMedico(id);
    }

    @Operation(summary = "Busca medicos por id, cpf, nome ou CRM", description = "Busca medicos por id, cpf, nome ou CRM")
    @GetMapping("/search")
    public List<Medico> searchMedico(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String crm){
        return medicoService.searchMedico(id, cpf, nome, crm);
    }
}
