package com.antonio.SistemadeAgendamentodeConsultas.unit.service;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.agendamento.AgendamentoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.enums.SituacaoAgendamento;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Agendamento;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import com.antonio.SistemadeAgendamentodeConsultas.repository.AgendamentoRepository;
import com.antonio.SistemadeAgendamentodeConsultas.repository.MedicoRepository;
import com.antonio.SistemadeAgendamentodeConsultas.repository.PacienteRepository;
import com.antonio.SistemadeAgendamentodeConsultas.service.AgendamentoService;
import com.antonio.SistemadeAgendamentodeConsultas.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    void agendarDeveSalvarAgendamentoEEnviarEmailDeConfirmacao() {
        Paciente paciente = criarPaciente();
        Medico medico = criarMedico();
        AgendamentoCreateDTO dto = new AgendamentoCreateDTO(
                "Primeira consulta",
                LocalDateTime.of(2026, 4, 10, 14, 30),
                "Clinica Central",
                "Rotina",
                SituacaoAgendamento.CONFIRMADO,
                1L,
                2L
        );

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(medicoRepository.findById(2L)).thenReturn(Optional.of(medico));
        when(agendamentoRepository.save(any(Agendamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Agendamento resultado = agendamentoService.agendar(dto);

        assertThat(resultado.getPaciente()).isEqualTo(paciente);
        assertThat(resultado.getMedico()).isEqualTo(medico);
        assertThat(resultado.getLocalConsulta()).isEqualTo("Clinica Central");

        ArgumentCaptor<String> assuntoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> htmlCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).enviarEmail(eq("paciente@email.com"), assuntoCaptor.capture(), htmlCaptor.capture());
        assertThat(assuntoCaptor.getValue()).contains("Agendamento");
        assertThat(htmlCaptor.getValue()).contains("Ana Paciente");
        assertThat(htmlCaptor.getValue()).contains("Dr(a). Dr. Joao");
        assertThat(htmlCaptor.getValue()).contains("10/04/2026");
        assertThat(htmlCaptor.getValue()).contains("Clinica Central");
    }

    @Test
    void agendarDeveFalharQuandoPacienteNaoExistir() {
        AgendamentoCreateDTO dto = new AgendamentoCreateDTO(
                "Retorno",
                LocalDateTime.of(2026, 4, 10, 14, 30),
                "Clinica Central",
                "Rotina",
                SituacaoAgendamento.PENDENTE,
                1L,
                2L
        );
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> agendamentoService.agendar(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paciente");

        verify(agendamentoRepository, never()).save(any(Agendamento.class));
        verify(emailService, never()).enviarEmail(any(String.class), any(String.class), any(String.class));
    }

    private Paciente criarPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNome("Ana Paciente");
        paciente.setCpf("12345678901");
        paciente.setTelefone("11999999999");
        paciente.setEmail("paciente@email.com");
        paciente.setDataNascimento(LocalDate.of(1992, 8, 15));
        paciente.setEndereco(criarEndereco());
        return paciente;
    }

    private Medico criarMedico() {
        Medico medico = new Medico();
        medico.setNome("Dr. Joao");
        medico.setCpf("10987654321");
        medico.setTelefone("11888888888");
        medico.setEmail("medico@email.com");
        medico.setDataNascimento(LocalDate.of(1980, 1, 10));
        medico.setEndereco(criarEndereco());
        medico.setCrm("654321-SP");
        return medico;
    }

    private Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Central");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setCidade("Sao Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01001000");
        return endereco;
    }
}
