package com.antonio.SistemadeAgendamentodeConsultas.DTOs.medico;

import com.antonio.SistemadeAgendamentodeConsultas.DTOs.endereco.EnderecoCreateDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para cadastro ou atualização de um médico")
public class MedicoCreateDTO {

    @NotBlank(message = "Nome e obrigatorio")
    @Schema(description = "Nome completo do médico", example = "Dr. João Pereira")
    private String nome;

    @NotBlank(message = "CPF e obrigatorio")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 digitos")
    @Schema(description = "CPF contendo 11 dígitos numéricos, sem pontuação", example = "98765432100")
    private String cpf;

    @NotBlank(message = "Telefone e obrigatorio")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone invalido")
    @Schema(description = "Telefone com DDD, apenas números", example = "11912345678")
    private String telefone;

    @Email(message = "E-mail invalido")
    @Schema(description = "E-mail de contato", example = "joao.pereira@email.com")
    private String email;

    @NotNull(message = "Data de nascimento e obrigatoria")
    @PastOrPresent(message = "Data de nascimento deve ser no passado ou presente")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "22/07/1985", type = "string")
    private LocalDate dataNascimento;

    @NotBlank(message = "CRM e obrigatorio")
    @Pattern(
            regexp = "^\\d{6}-[A-Z]{2}$",
            message = "CRM deve estar no formato 123456-AA"
    )
    @Schema(description = "Registro no Conselho Regional de Medicina, formato 123456-UF", example = "123456-SP")
    private String crm;

    @Valid
    private EnderecoCreateDTO endereco;
}
