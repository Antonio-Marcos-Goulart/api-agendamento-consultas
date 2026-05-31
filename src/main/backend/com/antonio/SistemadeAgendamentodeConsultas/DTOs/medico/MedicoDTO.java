package com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MedicoDTO {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String crm;
    private LocalDateTime dataDeCadastro;
    private String situacaoCadastro;

    public MedicoDTO(Medico medico) {
        this.id = medico.getId();
        this.nome = medico.getNome();
        this.cpf = medico.getCpf();
        this.dataNascimento = medico.getDataNascimento();
        this.email = medico.getEmail();
        this.telefone = medico.getTelefone();
        this.crm = medico.getCrm();
        this.dataDeCadastro = medico.getDataDeCadastro();
        this.situacaoCadastro = medico.getSituacaoCadastro() != null
                ? medico.getSituacaoCadastro().name()
                : null;
    }
}
