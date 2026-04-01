package com.antonio.SistemadeAgendamentodeConsultas.unit.service;

import com.antonio.SistemadeAgendamentodeConsultas.enums.SituacaoCadastro;
import com.antonio.SistemadeAgendamentodeConsultas.exception.MedicoNaoEncontradoExeption;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.repository.MedicoRepository;
import com.antonio.SistemadeAgendamentodeConsultas.service.MedicoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    @Test
    void createMedicoDeveFalharQuandoNomeForInvalido() {
        Medico medico = criarMedicoValido();
        medico.setNome("Al");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> medicoService.createMedico(medico))
                .withMessageContaining("3 caracteres");

        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void updateMedicoDeveAlterarSomenteCamposPreenchidos() {
        Medico existente = criarMedicoValido();
        Medico atualizado = new Medico();
        atualizado.setNome("Carlos Souza");
        atualizado.setEmail("carlos.souza@email.com");
        atualizado.setTelefone("11912345678");
        atualizado.setEndereco(criarEndereco("Rua Nova", "200"));

        when(medicoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(medicoRepository.save(any(Medico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medico resultado = medicoService.updateMedico(1L, atualizado);

        assertThat(resultado.getNome()).isEqualTo("Carlos Souza");
        assertThat(resultado.getEmail()).isEqualTo("carlos.souza@email.com");
        assertThat(resultado.getTelefone()).isEqualTo("11912345678");
        assertThat(resultado.getEndereco().getRua()).isEqualTo("Rua Nova");
        assertThat(resultado.getCrm()).isEqualTo("123456-SP");
    }

    @Test
    void searchMedicoDeveBuscarPorCrmQuandoDemaisFiltrosNaoForemInformados() {
        Medico medico = criarMedicoValido();
        when(medicoRepository.findByCrmContainingIgnoreCase("123456")).thenReturn(List.of(medico));

        List<Medico> resultado = medicoService.searchMedico(null, null, null, "123456");

        assertThat(resultado).containsExactly(medico);
    }

    @Test
    void deleteMedicoDeveLancarExcecaoQuandoIdNaoExistir() {
        when(medicoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> medicoService.deleteMedico(99L))
                .isInstanceOf(MedicoNaoEncontradoExeption.class)
                .hasMessageContaining("id: 99");
    }

    private Medico criarMedicoValido() {
        Medico medico = new Medico();
        medico.setNome("Carlos Lima");
        medico.setCpf("12345678901");
        medico.setTelefone("11999999999");
        medico.setEmail("carlos@email.com");
        medico.setDataNascimento(LocalDate.of(1985, 3, 10));
        medico.setEndereco(criarEndereco("Rua Medica", "10"));
        medico.setSituacaoCadastro(SituacaoCadastro.ATIVO);
        medico.setCrm("123456-SP");
        return medico;
    }

    private Endereco criarEndereco(String rua, String numero) {
        Endereco endereco = new Endereco();
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro("Centro");
        endereco.setCidade("Sao Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01001000");
        return endereco;
    }
}
