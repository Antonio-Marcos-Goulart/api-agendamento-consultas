package com.antonio.SistemadeAgendamentodeConsultas.repository;

import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findById(Long id);

}
