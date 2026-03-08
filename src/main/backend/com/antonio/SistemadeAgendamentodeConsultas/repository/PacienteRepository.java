package com.antonio.SistemadeAgendamentodeConsultas.repository;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByCpfContainingIgnoreCase(String cpf);
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
    Optional<Paciente> findById(Long id);
}
