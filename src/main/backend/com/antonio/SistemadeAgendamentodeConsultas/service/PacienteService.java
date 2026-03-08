package com.antonio.SistemadeAgendamentodeConsultas.service;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.paciente.PacienteCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.exception.PacienteNaoEncontadoException;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import com.antonio.SistemadeAgendamentodeConsultas.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente createPaciente(PacienteCreateDTO dto) {
        validarNome(dto.getNome());
        Paciente paciente = toEntity(dto);
        return pacienteRepository.save(paciente);
    }

    public Paciente getPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontadoException("Paciente não encontrado com id: " + id));
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente updatePaciente(Long id, PacienteCreateDTO dto) {
        Paciente existing = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontadoException("Paciente não encontrado com id: " + id));

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            validarNome(dto.getNome());
            existing.setNome(dto.getNome());
        }
        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            existing.setCpf(dto.getCpf());
        }
        if (dto.getTelefone() != null && !dto.getTelefone().isBlank()) {
            existing.setTelefone(dto.getTelefone());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            existing.setEmail(dto.getEmail());
        }
        if (dto.getDataNascimento() != null) {
            existing.setDataNascimento(dto.getDataNascimento());
        }
        if (dto.getEndereco() != null) {
            existing.setEndereco(toEndereco(dto.getEndereco()));
        }

        return pacienteRepository.save(existing);
    }

    public void deletePaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNaoEncontadoException("Paciente não encontrado com id: " + id));
        pacienteRepository.delete(paciente);
    }

    public List<Paciente> searchPaciente(Long id, String cpf, String nome) {
        List<Paciente> dadosSaidaPaciente;
        if (id != null) {
            Paciente paciente = pacienteRepository.findById(id).orElse(null);
            dadosSaidaPaciente = paciente != null ? List.of(paciente) : List.of();
        } else if (cpf != null && !cpf.isBlank()) {
            dadosSaidaPaciente = pacienteRepository.findByCpfContainingIgnoreCase(cpf);
        } else if (nome != null && !nome.isBlank()) {
            dadosSaidaPaciente = pacienteRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            dadosSaidaPaciente = pacienteRepository.findAll();
        }

        if (dadosSaidaPaciente.isEmpty()) {
            throw new PacienteNaoEncontadoException("Nenhum paciente encontrado com os critérios fornecidos.");
        }
        return dadosSaidaPaciente;
    }


    private void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do paciente deve ter pelo menos 3 caracteres");
        }
    }

    private Paciente toEntity(PacienteCreateDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEmail(dto.getEmail());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setEndereco(toEndereco(dto.getEndereco()));
        return paciente;
    }

    private Endereco toEndereco(@Valid EnderecoCreateDTO enderecoDto) {
        if (enderecoDto == null) return null;
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDto.getRua());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setBairro(enderecoDto.getBairro());
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setEstado(enderecoDto.getEstado());
        endereco.setCep(enderecoDto.getCep());
        return endereco;
    }
}