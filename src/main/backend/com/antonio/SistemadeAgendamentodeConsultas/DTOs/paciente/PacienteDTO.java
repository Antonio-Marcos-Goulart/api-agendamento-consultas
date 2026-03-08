package com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento.AgendamentoDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class PacienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String prontuario;
    private List<AgendamentoDTO> agendamentos = new ArrayList<>();

    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.cpf = paciente.getCpf();
        this.dataNascimento = paciente.getDataNascimento();
        this.email = paciente.getEmail();
        this.telefone = paciente.getTelefone();
        this.prontuario = paciente.getProntuario();

        // Converte lista de agendamentos evitando LazyInitializationException
        if (paciente.getAgendamentoList() != null) {
            this.agendamentos = paciente.getAgendamentoList()
                .stream()
                .map(AgendamentoDTO::new)
                .toList();
        }
    }

    public @Valid EnderecoCreateDTO getEndereco() {
        return null;
    }
}

