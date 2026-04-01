package com.antonio.SistemadeAgendamentodeConsultas.unit.service;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.exception.PacienteNaoEncontadoException;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import com.antonio.SistemadeAgendamentodeConsultas.repository.PacienteRepository;
import com.antonio.SistemadeAgendamentodeConsultas.service.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void createPacienteDeveSalvarPacienteConvertidoDoDto() {
        PacienteCreateDTO dto = criarPacienteDtoValido();
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Paciente pacienteSalvo = pacienteService.createPaciente(dto);

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        verify(pacienteRepository).save(captor.capture());
        Paciente pacientePersistido = captor.getValue();

        assertThat(pacienteSalvo).isSameAs(pacientePersistido);
        assertThat(pacientePersistido.getNome()).isEqualTo(dto.getNome());
        assertThat(pacientePersistido.getCpf()).isEqualTo(dto.getCpf());
        assertThat(pacientePersistido.getTelefone()).isEqualTo(dto.getTelefone());
        assertThat(pacientePersistido.getEmail()).isEqualTo(dto.getEmail());
        assertThat(pacientePersistido.getDataNascimento()).isEqualTo(dto.getDataNascimento());
        assertThat(pacientePersistido.getEndereco().getCidade()).isEqualTo(dto.getEndereco().getCidade());
    }

    @Test
    void createPacienteDeveFalharQuandoNomeForMuitoCurto() {
        PacienteCreateDTO dto = criarPacienteDtoValido();
        dto.setNome("Al");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> pacienteService.createPaciente(dto))
                .withMessage("O nome do paciente deve ter pelo menos 3 caracteres");

        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    void updatePacienteDeveAtualizarApenasCamposInformados() {
        Paciente existente = criarPacienteExistente();
        PacienteCreateDTO atualizacao = new PacienteCreateDTO(
                "Maria Souza",
                null,
                "",
                "maria.souza@email.com",
                null,
                null
        );
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Paciente atualizado = pacienteService.updatePaciente(1L, atualizacao);

        assertThat(atualizado.getNome()).isEqualTo("Maria Souza");
        assertThat(atualizado.getCpf()).isEqualTo("12345678901");
        assertThat(atualizado.getTelefone()).isEqualTo("11999999999");
        assertThat(atualizado.getEmail()).isEqualTo("maria.souza@email.com");
    }

    @Test
    void searchPacienteDeveLancarExcecaoQuandoNaoEncontrarResultados() {
        when(pacienteRepository.findByNomeContainingIgnoreCase("Inexistente")).thenReturn(List.of());

        assertThatThrownBy(() -> pacienteService.searchPaciente(null, null, "Inexistente"))
                .isInstanceOf(PacienteNaoEncontadoException.class)
                .hasMessageContaining("Nenhum paciente encontrado");
    }

    private PacienteCreateDTO criarPacienteDtoValido() {
        return PacienteCreateDTO.builder()
                .nome("Maria Silva")
                .cpf("12345678901")
                .telefone("11999999999")
                .email("maria@email.com")
                .dataNascimento(LocalDate.of(1995, 5, 20))
                .endereco(new EnderecoCreateDTO("Rua A", "123", "Apto 1", "Centro", "Sao Paulo", "SP", "01001000"))
                .build();
    }

    private Paciente criarPacienteExistente() {
        Paciente paciente = new Paciente();
        paciente.setNome("Maria Antiga");
        paciente.setCpf("12345678901");
        paciente.setTelefone("11999999999");
        paciente.setEmail("maria.antiga@email.com");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));
        paciente.setEndereco(criarPacienteDtoValido().getEndereco() == null ? null : pacienteServiceTestSupportEndereco());
        return paciente;
    }

    private com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco pacienteServiceTestSupportEndereco() {
        com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco endereco =
                new com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco();
        endereco.setRua("Rua A");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Sao Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01001000");
        return endereco;
    }
}
