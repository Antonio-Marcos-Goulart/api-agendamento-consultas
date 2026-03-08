package com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento;

import com.antonio.SistemadeAgendamentodeConsultas.enums.SituacaoAgendamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoCreateDTO {

    private String observacoes;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataAgendamento;

    @NotBlank
    private String localConsulta;

    @NotBlank
    private String tipoAgendamento;

    @NotNull
    private SituacaoAgendamento statusAgendamento;

    @NotNull
    private Long pacienteId;

    @NotNull
    private Long medicoId;
}