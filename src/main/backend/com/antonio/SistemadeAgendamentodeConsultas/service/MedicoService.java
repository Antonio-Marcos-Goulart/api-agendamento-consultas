package com.antonio.SistemadeAgendamentodeConsultas.service;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico.MedicoCreateDTO;
import com.antonio.SistemadeAgendamentodeConsultas.exception.MedicoNaoEncontradoExeption;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Endereco;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import com.antonio.SistemadeAgendamentodeConsultas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Medico createMedico(MedicoCreateDTO dto) {
        Medico medico = new Medico();

        medico.setNome(dto.getNome());
        medico.setCpf(dto.getCpf());
        medico.setTelefone(dto.getTelefone());
        medico.setEmail(dto.getEmail());
        medico.setDataNascimento(dto.getDataNascimento());
        medico.setCrm(dto.getCrm());

        if (dto.getEndereco() != null) {
            medico.setEndereco(toEndereco(dto.getEndereco()));
        }

        if (medico.getNome() == null || medico.getNome().length() < 3) {
            throw new IllegalArgumentException("O nome do médico deve ter pelo menos 3 caracteres");
        }
        return medicoRepository.save(medico);
    }

    private Endereco toEndereco(EnderecoCreateDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        return endereco;
    }

    public Medico getMedicoById(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoExeption("Médico não encontrado com id: " + id));
    }

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    // Atualiza: Nome, endereço, email, telefone, função e salário
    public Medico updateMedico(Long id, MedicoCreateDTO updatedMedico) {
        Medico existingMedico = medicoRepository.findById(id)
                .orElseThrow(() -> new MedicoNaoEncontradoExeption("Medico não encontrado com id: " + id));

        if (updatedMedico.getNome() != null && !updatedMedico.getNome().isEmpty()) {
            existingMedico.setNome(updatedMedico.getNome());
        }

        if (updatedMedico.getEndereco() != null) {
            existingMedico.setEndereco(toEndereco(updatedMedico.getEndereco()));
        }

        if (updatedMedico.getEmail() != null && !updatedMedico.getEmail().isEmpty()) {
            existingMedico.setEmail(updatedMedico.getEmail());
        }

        if (updatedMedico.getTelefone() != null && !updatedMedico.getTelefone().isEmpty()) {
            existingMedico.setTelefone(updatedMedico.getTelefone());
        }

        if (updatedMedico.getDataNascimento() != null) {
            existingMedico.setDataNascimento(updatedMedico.getDataNascimento());
        }

        if (updatedMedico.getCrm() != null && !updatedMedico.getCrm().isEmpty()) {
            existingMedico.setCrm(updatedMedico.getCrm());
        }

        return medicoRepository.save(existingMedico);
    }

    public void deleteMedico(Long id) {
        if (! medicoRepository.existsById(id)) {
            throw new MedicoNaoEncontradoExeption("Médico não encontrado com id: " + id);
        }
        medicoRepository.deleteById(id);
    }

    public List<Medico> buscarMedico(Long id, String cpf, String nome, String crm) {
        List<Medico> dadosSaidaMedico;

        if (id != null) {
            Medico medico = medicoRepository.findById(id).orElse(null);

            if (medico == null) {
                throw new MedicoNaoEncontradoExeption("Médico não encontrado.");
            }
            return List.of(medico);
        }

        if (cpf != null && !cpf.isBlank()) {
            dadosSaidaMedico = medicoRepository.findByCpfContainingIgnoreCase(cpf);
        } else if (nome != null && !nome.isBlank()) {
            dadosSaidaMedico = medicoRepository.findByNomeContainingIgnoreCase(nome);
        } else if (crm != null && !crm.isBlank()) {
            dadosSaidaMedico = medicoRepository.findByCrmContainingIgnoreCase(crm);
        } else {
            throw new IllegalArgumentException("Informe pelo menos um critério de pesquisa.");
        }

        if (dadosSaidaMedico.isEmpty()) {
            throw new MedicoNaoEncontradoExeption("Nenhum médico encontrado com os critérios fornecidos.");
        }

        return dadosSaidaMedico;
    }
}
