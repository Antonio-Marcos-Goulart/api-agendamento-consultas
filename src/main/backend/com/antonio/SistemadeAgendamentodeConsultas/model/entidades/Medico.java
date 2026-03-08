package com.antonio.SistemadeAgendamentodeConsultas.model.entidades;

import com.antonio.SistemadeAgendamentodeConsultas.model.abstratos.Pessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "medico", schema = "agendamento")
public class Medico extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(
            regexp = "^\\d{6}-[A-Z]{2}$", // Regex para o CRM
            message =  "CRM deve estar no formato 123456-AA (6 números, hífen e UF em letras maiúsculas)"
    )
    @Column(name = "crm", nullable = false, unique = true) // CRM obrigatório e único
    private String crm;

}
