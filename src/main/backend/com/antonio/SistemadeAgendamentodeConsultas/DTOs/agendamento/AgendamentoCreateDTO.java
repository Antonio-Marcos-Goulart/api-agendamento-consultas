package com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento;

import com.antonio.SistemadeAgendamentodeConsultas.enums.SituacaoAgendamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criação de um agendamento de consulta")
public class AgendamentoCreateDTO {

    @Schema(description = "Observações adicionais sobre a consulta (opcional)", example = "Paciente relatou dor lombar")
    private String observacoes;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "Data e hora da consulta no formato dd/MM/yyyy HH:mm:ss, deve ser presente ou futura",
            example = "25/12/2026 14:30:00", type = "string")
    private LocalDateTime dataAgendamento;

    @NotBlank
    @Schema(description = "Local onde a consulta será realizada", example = "Clínica Central - Sala 3")
    private String localConsulta;

    @NotBlank
    @Schema(description = "Tipo da consulta", example = "Retorno")
    private String tipoAgendamento;

    @NotNull
    @Schema(description = "Situação atual do agendamento")
    private SituacaoAgendamento statusAgendamento;

    @NotNull
    @Schema(description = "Id do paciente vinculado ao agendamento", example = "1")
    private Long pacienteId;

    @NotNull
    @Schema(description = "Id do médico vinculado ao agendamento", example = "1")
    private Long medicoId;
}