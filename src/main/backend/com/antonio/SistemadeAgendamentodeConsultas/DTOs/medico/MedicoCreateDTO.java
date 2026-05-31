package com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoCreateDTO {

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @NotBlank(message = "CPF e obrigatorio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 digitos")
    private String cpf;

    @NotBlank(message = "Telefone e obrigatorio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone invalido")
    private String telefone;

    @Email(message = "E-mail invalido")
    private String email;

    @NotNull(message = "Data de nascimento e obrigatoria")
    @PastOrPresent(message = "Data de nascimento deve ser no passado ou presente")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataNascimento;

    @NotBlank(message = "CRM e obrigatorio")
    @Pattern(
            regexp = "^\\d{6}-[A-Z]{2}$",
            message = "CRM deve estar no formato 123456-AA"
    )
    private String crm;

    @Valid
    private EnderecoCreateDTO endereco;
}
