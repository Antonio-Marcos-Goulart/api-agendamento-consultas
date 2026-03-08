package com.antonio.SistemadeAgendamentodeConsultas.repository;
import com.antonio.SistemadeAgendamentodeConsultas.model.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByCpfContainingIgnoreCase(String cpf);

    List<Medico> findByNomeContainingIgnoreCase(String nome);

    List<Medico> findByCrmContainingIgnoreCase(String crm);

    Optional<Medico> findById(Long id);


}
