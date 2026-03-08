package com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {
    private Long id;
    private LocalDateTime dtAgendamento;
    private String localConsulta;
    private String statusAgendamento;
    private String tipoAgendamento;
    private Long medicoId; // Apenas o ID do médico, evita serializar tudo

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.dtAgendamento = agendamento.getDataAgendamento();
        this.localConsulta = agendamento.getLocalConsulta();
        this.statusAgendamento = String.valueOf(agendamento.getStatusAgendamento());
        this.tipoAgendamento = agendamento.getTipoAgendamento();
        this.medicoId = agendamento.getMedico() != null ? agendamento.getMedico().getId() : null;
    }
}
