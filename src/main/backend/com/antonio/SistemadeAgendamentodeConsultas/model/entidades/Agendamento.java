package com.antonio.SistemadeAgendamentodeConsultas.model.entidades;

import com.antonio.SistemadeAgendamentodeConsultas.enums.SituacaoAgendamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "agendamento", schema = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observacao", nullable = false, length = 300)
    private String observacoes;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @FutureOrPresent(message = "A data de agendamento deve ser no futuro ou presente") // @FutureOrPresent = a data deve ser no futuro ou no presente
    @NotNull(message = "A data de agendamento não pode ser nula")
    @Column(name = "dt_agendamento", nullable = false)
    private LocalDateTime dataAgendamento;

    @NotNull(message = "Local da consulta não pode ser nulo")
    @Column(name = "local_consulta", nullable = false, length = 100)
    private String localConsulta;

    @NotNull(message = "Tipo do agendamento não pode ser nulo") // Criar um ENUM com os tipos de agendamentos
    @Column(name = "tipo_agendamento", nullable = false)
    private String tipoAgendamento;

    @NotNull(message = "Status do agendamento não pode ser nulo")
    @Enumerated(EnumType.STRING) // @Enumerated(EnumType.STRING) = armazena o nome do enum como string no banco de dados
    @Column(name = "status_agendamento", nullable = false)
    private SituacaoAgendamento statusAgendamento;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

}

