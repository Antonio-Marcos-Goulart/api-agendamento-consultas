package com.antonio.SistemadeAgendamentodeConsultas.service;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento.AgendamentoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.*;
import com.antonio.SistemadeAgendamentodeConsultas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EmailService emailService;

    public Agendamento agendar(AgendamentoCreateDTO dto) {
    // Converte DTO → Entidade
        Agendamento agendamento = new Agendamento();
        agendamento.setObservacoes(dto.getObservacoes());
        agendamento.setDataAgendamento(dto.getDataAgendamento());
        agendamento.setLocalConsulta(dto.getLocalConsulta());
        agendamento.setTipoAgendamento(dto.getTipoAgendamento());
        agendamento.setStatusAgendamento(dto.getStatusAgendamento());

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        agendamento.setPaciente(paciente);
        agendamento.setMedico(medico);

        Agendamento salvo = agendamentoRepository.save(agendamento);

        enviarEmailConfirmacao(salvo);
        return salvo;
    }

    private void enviarEmailConfirmacao(Agendamento agendamento) {
        String email = agendamento.getPaciente().getEmail();
        String nomePaciente = agendamento.getPaciente().getNome();
        String nomeMedico = agendamento.getMedico().getNome();
        String dataHora = agendamento.getDataAgendamento()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
        String local = agendamento.getLocalConsulta();

        String html = """
            <div style="font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;">
                <h2 style="color: #2c3e50; text-align: center;">Consulta Agendada com Sucesso!</h2>
                <p>Olá <strong>%s</strong>,</p>
                <p>Sua consulta foi agendada com sucesso:</p>
                <ul>
                    <li><strong>Data/Hora:</strong> %s</li>
                    <li><strong>Médico(a):</strong> Dr(a). %s</li>
                    <li><strong>Local:</strong> %s</li>
                </ul>
                <p><em>Chegue 15 minutos antes.</em></p>
                <hr>
                <small style="color: #777;">Este é um e-mail automático. Não responda.</small>
            </div>
            """.formatted(nomePaciente, dataHora, nomeMedico, local);

        emailService.enviarEmail(email, "Confirmação de Agendamento", html);
    }
}