package com.antonio.SistemadeAgendamentodeConsultas.service;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Agendamento;
import com.antonio.SistemadeAgendamentodeConsultas.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class EnvioNotificacaoEmailService {

    @Autowired
    private final EmailService emailService;
    private final TaskScheduler taskScheduler;

    public EnvioNotificacaoEmailService(TaskScheduler taskScheduler, EmailService emailService) {
        this.taskScheduler = taskScheduler;
        this.emailService = emailService;
    }

    public void lembreteAgendamentoConsulta(Agendamento agendamento) {
        LocalDateTime dataLembrete = agendamento.getDataAgendamento().minusDays(1); // Vai subtrair 1 dia da data de agendamento -- assim envia o email 24h antes

        Instant instant = dataLembrete.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();

        taskScheduler.schedule(() -> {
            emailService.enviarEmail(
                    agendamento.getPaciente().getEmail(),
                    "Confirmação de Consulta",
                    "Olá " + agendamento.getPaciente().getNome() + ", sua consulta é em " + agendamento.getDataAgendamento() +
                            " com o(a) Dr(a) " + agendamento.getMedico().getNome()
            );
        }, instant);
    }
}
